package com.dinghao.service.template.products.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dinghao.dao.template.products.ProductsDao;
import com.dinghao.entity.template.products.Products;
import com.dinghao.entity.vo.template.JqGridVo;
import com.dinghao.entity.vo.template.products.ProductsVo;
import com.dinghao.service.template.products.ProductsService;

@Service
public class ProductsServiceImp implements ProductsService {

	@Resource
	ProductsDao productsDao;

	@Override
	public Products selectByPrimaryKey(Long id) {
		return productsDao.selectByPrimaryKey(id);
	}

	/**
	 * 获取商品信息列表
	 */
	@Override
	public JqGridVo<Products> selectProducts(ProductsVo productsVo) {
		JqGridVo<Products> jqGridVo = new JqGridVo<Products>(productsVo);
		jqGridVo.setList(productsDao.selectByStatement(productsVo));
		jqGridVo.setRecords(productsDao.selectByStatementCount(productsVo));
		return jqGridVo;
	}

	@Override
	public int updateProducts(ProductsVo productsVo) {
		return productsDao.updateByPrimaryKeySelective(productsVo);
	}

	@Override
	public int insertProducts(ProductsVo productsVo) {
		return productsDao.insert(productsVo);
	}

	@Override
	public int deleteProducts(long id) {
		return productsDao.deleteByPrimaryKey(id);
	}

}
