package com.dinghao.entity.manage.menuroleauthorization.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinghao.dao.manage.MenuRoleAuthorization.MenuRoleAuthorizationDao;
import com.dinghao.dao.manage.role.MenuRoleDao;
import com.dinghao.entity.manage.menuroleauthorization.MenuRoleAuthorization;
import com.dinghao.entity.manage.role.MenuRole;
import com.dinghao.service.manage.menuroleauthorization.MenuRoleAuthorizationService;

@Service
public class MenuRoleAuthorizationServiceImp implements
		MenuRoleAuthorizationService {

	@Autowired
	private MenuRoleAuthorizationDao menuRoleAuthorizationDao;
	@Autowired
	private MenuRoleDao menuRoleDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long insertSelective(MenuRoleAuthorization record, MenuRole menuRole) {
		if (record.getMenuRoleId() == null) {
			// 先检测是否已经入库
			int count = menuRoleDao.selectByStatementCount(menuRole);
			if (count > 0) {
				menuRole = menuRoleDao.selectByStatement(menuRole).get(0);
			} else {
				menuRoleDao.insertSelective(menuRole);
			}
		}
		record.setMenuRoleId(menuRole.getId());
		menuRoleAuthorizationDao.insertSelective(record);
		return menuRole.getId();
	}

	@Override
	public List<MenuRoleAuthorization> selectByStatement(
			MenuRoleAuthorization record) {
		return menuRoleAuthorizationDao.selectByStatement(record);
	}

	@Override
	public List<MenuRoleAuthorization> selectByStatementIds(Long roles,
			Long menus) {
		return menuRoleAuthorizationDao.selectByStatementIds(roles, menus);
	}

	@Override
	public void deleteByPrimaryKey(Long roleId, Long menuId) {
		// TODO Auto-generated method stub
		menuRoleAuthorizationDao.deleteAllByPrimaryKey(roleId,menuId);
	}
}
