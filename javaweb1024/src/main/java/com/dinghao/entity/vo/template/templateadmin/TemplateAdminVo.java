/*
* @ClassName:TemplateAdmin.java
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author: 
*-----------Zihan--www.javaweb1024.com 版权所有------------
* @date 2016-10-12
*/
package com.dinghao.entity.vo.template.templateadmin;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.dinghao.entity.vo.manage.PageVo;

public class TemplateAdminVo extends PageVo<TemplateAdminVo> {
    /**
	 * @Fields serialVersionUID : (用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;

	/**
     * .主键
     */
    private Long id;

    /**
     * .创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDate;

    /**
     * .修改时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date modifyDate;

    /**
     * .所在部门
     */
    private String department;

    /**
     * .邮箱
     */
    private String email;

    /**
     * .是否可用 1不可用,0可用
     */
    private Boolean isEnabled;

    /**
     * .是否被锁,1被锁定,0未锁
     */
    private Boolean isLocked;

    /**
     * .锁定时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lockedDate;

    /**
     * .登录时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date loginDate;

    /**
     * .失败次数
     */
    private Integer loginFailureCount;

    /**
     * .登录IP
     */
    private String loginIp;

    /**
     * .真实姓名
     */
    private String name;

    /**
     * .密码
     */
    private String password;

    /**
     * .用户名
     */
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = StringUtils.isBlank(department)? null : department.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = StringUtils.isBlank(email)? null : email.trim();
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = StringUtils.isBlank(loginIp)? null : loginIp.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtils.isBlank(name)? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = StringUtils.isBlank(password)? null : password.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = StringUtils.isBlank(username)? null : username.trim();
    }
}