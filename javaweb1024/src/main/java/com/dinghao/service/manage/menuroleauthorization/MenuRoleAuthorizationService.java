package com.dinghao.service.manage.menuroleauthorization;

import java.util.List;

import com.dinghao.entity.manage.menuroleauthorization.MenuRoleAuthorization;
import com.dinghao.entity.manage.role.MenuRole;

public interface MenuRoleAuthorizationService {
	int deleteByPrimaryKey(Long id);

    Long insertSelective(MenuRoleAuthorization record, MenuRole menuRole);

    List<MenuRoleAuthorization> selectByStatement(MenuRoleAuthorization record);

	List<MenuRoleAuthorization> selectByStatementIds(Long roles, Long menus);

	void deleteByPrimaryKey(Long roleId, Long menuId);

}
