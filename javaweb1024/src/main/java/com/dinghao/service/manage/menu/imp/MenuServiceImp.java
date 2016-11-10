package com.dinghao.service.manage.menu.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinghao.dao.manage.menu.MenuDao;
import com.dinghao.entity.manage.menu.Menu;
import com.dinghao.entity.vo.manage.PageVo;
import com.dinghao.entity.vo.manage.menuvo.MenuVo;
import com.dinghao.service.manage.menu.MenuService;

@Service
@Transactional
public class MenuServiceImp implements MenuService {
	@Autowired
	MenuDao menuDao;

	@Override
	@CacheEvict(value = { "findParentMenu", "findChildrenMenu",
			"findParentMenuByAdminId","findGrandParentsMenuByAdminId" }, allEntries = true)
	public int insertSelective(MenuVo record) {
		// 插入时间
		record.setCreateDate(new Date());
		menuDao.insertSelective(record);
		return record.getId().intValue();
	}

	@Override
	@CacheEvict(value = { "findParentMenu", "findChildrenMenu",
			"findParentMenuByAdminId","findGrandParentsMenuByAdminId" }, allEntries = true)
	public int updateByPrimaryKey(MenuVo record) {
		record.setModifyDate(new Date());
		return menuDao.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// 清除角色导航信息
		menuDao.deleteMenuRoleByMenuId(id);
		// 清除所有子节点
		menuDao.deleteByParentId(id);
		return menuDao.deleteByPrimaryKey(id);
	}

	@Override
	public PageVo<Menu> findParentMenuPage(MenuVo menuVo) {
		PageVo<Menu> pageVo = new PageVo<Menu>();
		pageVo.setList(menuDao.findParentMenuPage(menuVo));
		pageVo.setCount(menuDao.findParentMenuCount());
		return pageVo;
	}

	@Override
	public PageVo<Menu> findParentMenu(MenuVo menuVo) {
		PageVo<Menu> pageVo = new PageVo<Menu>();
		pageVo.setList(menuDao.findParentMenu(menuVo));
		pageVo.setCount(menuDao.findParentMenuCount());
		return pageVo;
	}
	@Override
	@Cacheable(value = "findChildrenMenu")
	public List<Menu> findChildrenMenu(Integer id, Long adminId) {
		// 内置管理员 获取所有子菜单
		if (adminId == 1l) {
			return menuDao.findChildrenMenu(id);
		} else {
			return menuDao.findChildrenMenuByAdminId(id, adminId);
		}

	}

	@Override
	public Menu selectByPrimaryKey(MenuVo menuVo) {
		return menuDao.selectByPrimaryKey(menuVo);
	}

	@Override
	@Cacheable(value = "findParentMenuByAdminId")
	public List<Menu> findParentMenuByAdminId(Integer id) {
		return menuDao.findParentMenuByAdminId(id);
	}

	

	@Override
	@Cacheable(value = "findGrandParentsMenuByAdminId")
	public List<Menu> findGrandParentsMenuByAdminId(Set<Long> intValue) {
		List<Long> list = new ArrayList<Long>(intValue);
		return menuDao.findGrandParentsMenuByAdminId(list);
	}

	@Override
	public List<Menu> findMenuByAdminId(Long id) {
		return menuDao.findMenuByAdminId(id);
	}

}
