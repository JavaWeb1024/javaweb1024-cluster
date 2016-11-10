/*
* @ClassName:TemplateAdminDao.java
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author: 
*-----------Zihan--www.javaweb1024.com 版权所有------------
* @date 2016-10-12
*/
package com.dinghao.dao.template.templateadmin;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dinghao.entity.template.templateadmin.TemplateAdmin;
import com.dinghao.entity.vo.template.templateadmin.TemplateAdminVo;

@Repository
public interface TemplateAdminDao {
    int deleteByPrimaryKey(Long id);

    int insert(TemplateAdminVo record);

    int insertSelective(TemplateAdminVo record);

    TemplateAdmin selectByPrimaryKey(Long id);

    List<TemplateAdmin> selectByStatement(TemplateAdminVo record);

    int selectByStatementCount(TemplateAdminVo record);

    int updateByPrimaryKeySelective(TemplateAdminVo record);
    
    TemplateAdmin selectByUserName(@Param("userName")String userName);
}