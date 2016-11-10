/*
 * @ClassName:MenuRoleAuthorization.java
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author: 
 *-----------Zihan--www.javaweb1024.com 版权所有------------
 * @date 2016-06-30
 */
package com.dinghao.entity.manage.menuroleauthorization;

import org.apache.commons.lang3.StringUtils;

import com.dinghao.entity.BaseEntity;

public class MenuRoleAuthorization extends BaseEntity {
	/**
	 * @Fields serialVersionUID : (用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * .菜单与角色中间表主键
	 */
	private Long menuRoleId;

	/**
	 * .权限名称
	 */
	private String authorizationName;

	/**
	 * .权限名称描述
	 */
	private String authorizationDescription;

	public Long getMenuRoleId() {
		return menuRoleId;
	}

	public void setMenuRoleId(Long menuRoleId) {
		this.menuRoleId = menuRoleId;
	}

	public String getAuthorizationName() {
		return authorizationName;
	}

	public void setAuthorizationName(String authorizationName) {
		this.authorizationName = StringUtils.isBlank(authorizationName) ? null
				: authorizationName.trim();
	}

	public String getAuthorizationDescription() {
		return authorizationDescription;
	}

	public void setAuthorizationDescription(String authorizationDescription) {
		this.authorizationDescription = StringUtils
				.isBlank(authorizationDescription) ? null
				: authorizationDescription.trim();
	}
}