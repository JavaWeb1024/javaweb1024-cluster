/*
* @ClassName:ProductsDao.java
* @Description: TODO(������һ�仰��������������) 
* @author: 
*-----------Zihan--www.javaweb1024.com ��Ȩ����------------
* @date 2016-10-13
*/
package com.dinghao.dao.template.products;

import com.dinghao.entity.template.products.Products;
import java.util.List;

public interface ProductsDao {
    int deleteByPrimaryKey(Long id);

    int insert(Products record);

    int insertSelective(Products record);

    Products selectByPrimaryKey(Long id);

    List<Products> selectByStatement(Products record);

    int selectByStatementCount(Products record);

    int updateByPrimaryKeySelective(Products record);
}