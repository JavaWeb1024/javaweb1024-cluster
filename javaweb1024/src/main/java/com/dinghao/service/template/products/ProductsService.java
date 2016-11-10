package com.dinghao.service.template.products;

import com.dinghao.entity.template.products.Products;
import com.dinghao.entity.vo.template.JqGridVo;
import com.dinghao.entity.vo.template.products.ProductsVo;

public interface ProductsService {

	/**
	 * 
	 * @Title: selectByPrimaryKey
	 * @Description: TODO(依据主键获取Products对象)
	 * @param @param id
	 * @param @return 设定文件
	 * @return Receipt 返回类型
	 * @throws
	 */
	public Products selectByPrimaryKey(Long id);

	/**
	 * 
	 * @Title: getReceiptDetails
	 * @Description: TODO(获取Products对象列表)
	 * @param @param receiptDetailVo
	 * @param @return 设定文件
	 * @return JqGridVo<Products> 返回类型
	 * @throws
	 */
	public JqGridVo<Products> selectProducts(
			ProductsVo productsVo);

	/**
	 * 
	 * @Title: updateProducts
	 * @Description: TODO(更新)
	 * @param @param ProductsVo
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public int updateProducts(ProductsVo productsVo);

	/**
	 * 
	 * @Title: insertProducts
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param receiptDetailVo
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public int insertProducts(ProductsVo productsVo);
	
	public int deleteProducts(long id);

}
