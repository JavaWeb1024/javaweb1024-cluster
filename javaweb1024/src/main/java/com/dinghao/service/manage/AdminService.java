package com.dinghao.service.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinghao.constant.CommonConstant;
import com.dinghao.constant.SystemConstant;
import com.dinghao.dao.manage.admin.AdminDao;
import com.dinghao.entity.manage.admin.Admin;
import com.dinghao.entity.manage.menu.Menu;
import com.dinghao.entity.manage.menuroleauthorization.MenuRoleAuthorization;
import com.dinghao.entity.manage.role.Role;
import com.dinghao.entity.vo.manage.PageVo;
import com.dinghao.entity.vo.manage.adminvo.AdminVo;
import com.dinghao.exception.AuthException;
import com.dinghao.exception.ValidateException;
import com.dinghao.service.manage.menu.MenuService;
import com.dinghao.service.manage.role.RoleService;
import com.dinghao.util.AuthUtils;
import com.dinghao.util.PropertyUtils;

/**
 * 管理员
 * 
 * @author Administrator
 * 
 */
@Service
public class AdminService {

	protected final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private AdminDao dhAdminDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	// ///////////////////////////////
	// ///// 增加 ////////
	// ///////////////////////////////

	/**
	 * 添加管理员
	 * 
	 * @param roleIds
	 * 
	 * @param email
	 * @param name
	 * @param password
	 * @return Admin
	 */
	public Long addAdmin(AdminVo adminVo, Long[] roleIds) {
		Date now = new Date();
		adminVo.setPassword(AuthUtils.getPassword(adminVo.getPassword()));
		adminVo.setCreateDate(now);
		try {
			dhAdminDao.insert(adminVo);
			// 添加用户 角色信息表
			this.addAdminRoles(adminVo.getId(), roleIds);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		return adminVo.getId();
	}

	/**
	 * 删除管理员
	 * 
	 * @param adminId
	 * @return Integer
	 */
	public int deleteAdmin(long adminId) {
		// 删除角色信息表
		int i = dhAdminDao.deleteAdminRoleByAdminId(adminId);
		if (i > 0) {
			// 删除管理员表
			dhAdminDao.deleteByPrimaryKey(adminId);
		}
		return i;
	}

	// ///////////////////////////////
	// ///// 修改 ////////
	// ///////////////////////////////

	/**
	 * 修改管理员资料
	 * 
	 * @param adminId
	 * @param name
	 * @param password
	 * @param status
	 * @return Admin
	 * @throws AuthException
	 * @throws ValidateException
	 */
	public void updateAdminByAmdinId(AdminVo adminVo, Long[] roleIds)
			throws AuthException, ValidateException {
		if (StringUtils.isNotBlank(adminVo.getPassword())) {
			adminVo.setPassword(AuthUtils.getPassword(adminVo.getPassword()));
		}
		adminVo.setModifyDate(new Date());
		int result = dhAdminDao.updateByPrimaryKeySelective(adminVo);
		// 更新成功
		if (result == 1) {
			// 清除原有的数据
			dhAdminDao.deleteAdminRoleByAdminId(adminVo.getId());
			this.addAdminRoles(adminVo.getId(), roleIds);
		} else {
			throw new ValidateException("修改失败！");
		}
	}

	/**
	 * 通过Id获得指定管理员资料
	 */
	public Admin getAdminById(long adminId) {
		Admin admin = dhAdminDao.selectByPrimaryKey(adminId);
		// 获取rols对象集
		List<Role> roles = roleService.getRolesByadminId(adminId);
		List<Long> list = new ArrayList<Long>();
		for (Role role : roles) {
			list.add(role.getId());
		}
		admin.setRoles(list);
		return admin;
	}

	/**
	 * 获得所有管理员的分页数据
	 * 
	 * @param offset
	 * @param rows
	 * @return List<Admin>
	 */
	public List<Admin> getAllList(long offset, long rows) {
		return dhAdminDao.getAllList(offset, rows);
	}

	/**
	 * 获得所有管理员的数量
	 * 
	 * @return Integer
	 */
	public int getAllListCount() {
		return dhAdminDao.getAllListCount();
	}

	/**
	 * 获得所有管理员的分页
	 * 
	 * @param Integer
	 * @return PageVo<Admin>
	 */
	public PageVo<Admin> getAllListPage(int pageNum) {
		PageVo<Admin> pageVo = new PageVo<Admin>(pageNum);
		pageVo.setRows(20);
		List<Admin> list = this
				.getAllList(pageVo.getOffset(), pageVo.getRows());
		pageVo.setList(list);
		pageVo.setCount(this.getAllListCount());
		return pageVo;
	}

	/**
	 * 通过用户名获得管理员资料
	 * 
	 * @param email
	 * @return Admin
	 */
	@Cacheable(value = "getAdminByName")
	public Admin getAdminByName(String name) {
		return dhAdminDao.getAdminByName(name);
	}

	public long getSuperAdminId() {
		Admin admin = getAdminByName(PropertyUtils.getValue("dinghao.admin"));
		return admin.getId();
	}

	public boolean usernameExists(String username) {
		Admin admin = dhAdminDao.getAdminByName(username);
		if (admin != null) {
			return true;
		} else {
			return false;
		}

	}

	public void addAdminRoles(Long id, Long[] roleIds) {
		for (Long long1 : roleIds) {
			dhAdminDao.addAdminRoles(id, long1);
		}

	}

	@Transactional
	public void updatePasswordByAmdinId(Long id, String text) {
		dhAdminDao.updatePasswordByAmdinId(id, text);
	}

	public List<String> findAuthorities(Long id) {
		List<String> authorities = new ArrayList<String>();
		Admin admin = dhAdminDao.selectByPrimaryKey(id);
		List<Menu> menus = menuService.findMenuByAdminId(id);
		if (admin != null) {
			for (Menu menu : menus) {
				if (menu != null
						&& StringUtils.isNotBlank(menu.getAuthorities()))
					authorities.add(menu.getAuthorities());
			}
		}
		// 获取按钮级别权限
		List<MenuRoleAuthorization> menuRoleAuthorizations = roleService
				.getMenuRoleAuthorizationByAdminId(id);
		for (MenuRoleAuthorization menuRoleAuthorization : menuRoleAuthorizations) {
			if (menuRoleAuthorization != null
					&& StringUtils.isNotBlank(menuRoleAuthorization
							.getAuthorizationName())) {
				authorities.add(menuRoleAuthorization.getAuthorizationName());
			}
			logger.info("权限列表"+authorities.toString());

		}
		return authorities;
	}

}
