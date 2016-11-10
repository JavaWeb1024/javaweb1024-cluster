package com.dinghao.service.manage.role.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinghao.dao.manage.MenuRoleAuthorization.MenuRoleAuthorizationDao;
import com.dinghao.dao.manage.role.MenuRoleDao;
import com.dinghao.dao.manage.role.RoleDao;
import com.dinghao.entity.manage.menuroleauthorization.MenuRoleAuthorization;
import com.dinghao.entity.manage.role.MenuRole;
import com.dinghao.entity.manage.role.Role;
import com.dinghao.entity.vo.manage.PageVo;
import com.dinghao.entity.vo.manage.rolevo.RoleVo;
import com.dinghao.service.manage.role.RoleService;

@Service
@Transactional
public class RoleServiceImp implements RoleService {

	@Autowired
	RoleDao roleDao;
	@Autowired
	MenuRoleDao menuRoleDao;
	@Autowired
	private MenuRoleAuthorizationDao menuRoleAuthorizationDao;

	@Override
	public List<Role> getAllRole() {
		return roleDao.getAllList(0l, Long.MAX_VALUE);
	}

	@Override
	public List<Role> getRolesByadminId(long adminId) {
		return roleDao.getRolesByadminId(adminId);
	}

	@Override
	@CacheEvict(value = { "findParentMenuByAdminId",
			"findGrandParentsMenuByAdminId", "findChildrenMenu" }, allEntries = true)
	public Long insertSelective(RoleVo roleVo, Long[] menus) {
		// 添加角色信息
		roleVo.setCreateDate(new Date());
		roleVo.setIsSystem(false);
		roleDao.insertSelective(roleVo);
		if (menus != null) {
			// 保存导航信息表数据
			Map map = new HashMap();
			map.put("roleId", roleVo.getId());
			map.put("menus", menus);
			roleDao.insertMenuRoles(map);
		}
		return roleVo.getId();
	}

	@Override
	public PageVo<Role> getRoles(RoleVo roleVo) {
		PageVo<Role> pageVo = new PageVo<Role>();
		pageVo.setPageNum(roleVo.getPageNum());
		pageVo.setList(roleDao.getRoles(roleVo));
		pageVo.setCount(roleDao.getRolesCount(roleVo));
		return pageVo;
	}

	@Override
	public void deleteByPrimaryKey(Long id) {
		/*// 删除用户角色信息表dh_admin_role
		roleDao.deleteAdminRoleByRoleId(id);
		// 删除角色导航信息表 dh_menu_role
		roleDao.deleteMenuRoleByRoleId(id);
		// 删除角色信息表dh_role
		roleDao.deleteByPrimaryKey(id);*/
		
		roleDao.deleteAllByPrimaryKey(id);

	}

	@Override
	public Role selectByPrimaryKey(Long id) {
		return roleDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Long> findMenusByRoleId(Long id) {
		return roleDao.findMenusByRoleId(id);
	}

	@Override
	@CacheEvict(value = { "findParentMenuByAdminId",
			"findGrandParentsMenuByAdminId", "findChildrenMenu" }, allEntries = true)
	public void updateByPrimaryKey(RoleVo roleVo, Long[] menus) {
		// 删除角色导航信息表 dh_menu_role
		//roleDao.deleteMenuRoleByRoleId(roleVo.getId());
		// 依据主键修改角色表数据
		//roleDao.updateByPrimaryKeySelective(roleVo);
		// 保存导航信息表数据
		Map map = new HashMap();
		map.put("roleId", roleVo.getId());
		map.put("menus", menus);
		roleDao.insertMenuRoles(map);
	}

	@Override
	public void deleteMenuRoleByPrimaryKey(Long id) {
		menuRoleAuthorizationDao.deleteByPrimaryKey(id);
	}

	@Override
	public MenuRole getMenuRoles(MenuRole menuRole) {
		return menuRoleDao.selectByStatement(menuRole).get(0);
	}

	@Override
	public List<MenuRoleAuthorization> getMenuRoleAuthorizationByAdminId(Long id) {
		return menuRoleAuthorizationDao.findMenuRoleAuthorizationByAdminId(id);
	}

}
