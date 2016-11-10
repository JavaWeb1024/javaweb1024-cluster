/*
* @ClassName:MenuRole.java
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author: 
*-----------Zihan--www.javaweb1024.com 版权所有------------
* @date 2016-07-01
*/
package com.dinghao.entity.manage.role;

public class MenuRole {
    /**
     * .主键
     */
    private Long id;

    /**
     * .roles主键
     */
    private Long roles;

    /**
     * .menu表主键
     */
    private Long menus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoles() {
        return roles;
    }

    public void setRoles(Long roles) {
        this.roles = roles;
    }

    public Long getMenus() {
        return menus;
    }

    public void setMenus(Long menus) {
        this.menus = menus;
    }
}