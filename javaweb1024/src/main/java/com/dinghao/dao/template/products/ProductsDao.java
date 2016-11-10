/*
* @ClassName:ProductsDao.java
* @Description: TODO(������һ�仰��������������) 
* @author: 
*-----------Zihan--www.javaweb1024.com ��Ȩ����------------
* @date 2016-10-13
*/
package com.dinghao.dao.template.products;

import com.dinghao.entity.template.products.Products;
import com.dinghao.entity.vo.template.products.ProductsVo;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductsDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductsVo record);

    int insertSelective(ProductsVo record);

    Products selectByPrimaryKey(Long id);

    List<Products> selectByStatement(ProductsVo record);

    int selectByStatementCount(ProductsVo record);

    int updateByPrimaryKeySelective(ProductsVo record);
}