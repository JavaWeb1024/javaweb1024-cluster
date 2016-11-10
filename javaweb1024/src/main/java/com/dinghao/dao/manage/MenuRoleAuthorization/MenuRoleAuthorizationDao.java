/*
 * @ClassName:MenuRoleAuthorizationDao.java
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author: 
 *-----------Zihan--www.javaweb1024.com 版权所有------------
 * @date 2016-06-15
 */
package com.dinghao.dao.manage.MenuRoleAuthorization;

import com.dinghao.entity.manage.menuroleauthorization.MenuRoleAuthorization;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRoleAuthorizationDao {
	int deleteByPrimaryKey(Long id);

	int insert(MenuRoleAuthorization record);

	int insertSelective(MenuRoleAuthorization record);

	MenuRoleAuthorization selectByPrimaryKey(Long id);

	List<MenuRoleAuthorization> selectByStatement(MenuRoleAuthorization record);

	int selectByStatementCount(MenuRoleAuthorization record);

	int updateByPrimaryKeySelective(MenuRoleAuthorization record);

	List<MenuRoleAuthorization> selectByStatementIds(
			@Param("roles") Long roles, @Param("menus") Long menus);

	void deleteAllByPrimaryKey(@Param("roleId")Long roleId, @Param("menuId")Long menuId);

	List<MenuRoleAuthorization> findMenuRoleAuthorizationByAdminId(@Param("id")Long id);
}