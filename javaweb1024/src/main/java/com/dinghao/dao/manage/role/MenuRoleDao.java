/*
* @ClassName:MenuRoleDao.java
* @Description: TODO(������һ�仰��������������) 
* @author: 
*-----------Zihan--www.javaweb1024.com ��Ȩ����------------
* @date 2016-07-01
*/
package com.dinghao.dao.manage.role;

import com.dinghao.entity.manage.role.MenuRole;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface MenuRoleDao {
    int deleteByPrimaryKey(Long id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    MenuRole selectByPrimaryKey(Long id);

    List<MenuRole> selectByStatement(MenuRole record);

    int selectByStatementCount(MenuRole record);

    int updateByPrimaryKeySelective(MenuRole record);
}