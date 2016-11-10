package com.dinghao.action.template;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dinghao.action.BaseAction;
import com.dinghao.entity.template.products.Products;
import com.dinghao.entity.vo.JsonVo;
import com.dinghao.entity.vo.template.JqGridVo;
import com.dinghao.entity.vo.template.products.ProductsVo;
import com.dinghao.service.template.products.ProductsService;

@Controller
@RequestMapping("/template/product")
public class ProductAction extends BaseAction {

	@Resource
	ProductsService productsService;

	@RequestMapping(value = { "", "/index.jhtml" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap modelMap) {
		return themeService.getTemplatePath("product/index");
	}

	/***
	 * 新增商品页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/addProducts.jhtml", method = RequestMethod.GET)
	public String addProducts(HttpServletRequest request, ModelMap modelMap) {
		return themeService.getTemplatePath("product/addProducts");
	}

	/**
	 * 获取商品列表
	 * 
	 * @param productsVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProducts.jhtml", method = RequestMethod.POST)
	public JSONObject getWarehouses(ProductsVo productsVo, HttpServletRequest request) {
		productsVo.setTempAdminId(getTemplateAdmin().getId());
		JqGridVo<Products> jqGridVo = productsService.selectProducts(productsVo);
		return jqGridVo.getJSONObject();
	}

	@ResponseBody
	@RequestMapping(value = "/addProducts.jhtml", method = RequestMethod.POST)
	public JsonVo<String> addProducts(ProductsVo productsVo, HttpServletRequest request) {
		JsonVo<String> sJsonVo = new JsonVo<String>();
		productsVo.setTempAdminId(getTemplateAdmin().getId());
		productsVo.setCreateDate(new Date());
		int a = productsService.insertProducts(productsVo);
		if (a == 1) {
			sJsonVo.setResult(true);
			sJsonVo.setMsg("保存成功!");
		} else {
			sJsonVo.setResult(false);
			sJsonVo.setMsg("系统出错,保存失败!");
		}

		return sJsonVo;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteProducts.jhtml", method = RequestMethod.POST)
	public JsonVo<String> deleteProducts(Long id, HttpServletRequest request) {
		JsonVo<String> jsonVo = new JsonVo<String>();
		if (id == null) {
			jsonVo.setResult(false);
			jsonVo.setMsg("操作有误,请重新操作!");
			return jsonVo;
		}
		int i = productsService.deleteProducts(id);
		if (i != 1) {
			jsonVo.setResult(false);
			jsonVo.setMsg("操作有误,请重新操作!");
			return jsonVo;
		} else {
			jsonVo.setResult(true);
			jsonVo.setMsg("操作成功!");
		}
		return jsonVo;
	}

	@RequestMapping(value = "/update_products.jhtml", method = RequestMethod.GET)
	public String updateProducts(HttpServletRequest request, ModelMap modelMap, long id) {
		Products products = productsService.selectByPrimaryKey(id);
		if (products == null) {
			modelMap.put("result", false);
		}
		modelMap.put("products", products);
		return themeService.getTemplatePath("product/updateProducts");
	}

	/**
	 * 修改商品信息
	 * 
	 * @param productsVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update_products.jhtml", method = RequestMethod.POST)
	public JsonVo<String> updateProducts(ProductsVo productsVo, HttpServletRequest request) {
		JsonVo<String> sJsonVo = new JsonVo<String>();
		productsVo.setModifyDate(new Date());
		int a = productsService.updateProducts(productsVo);
		if (a == 1) {
			sJsonVo.setResult(true);
			sJsonVo.setMsg("修改成功!");
		} else {
			sJsonVo.setResult(false);
			sJsonVo.setMsg("修改出错,保存失败!");
		}

		return sJsonVo;
	}

	/**
	 * 商品信息展示
	 * 
	 * @param request
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/show_products.jhtml", method = RequestMethod.GET)
	public String showProducts(HttpServletRequest request, ModelMap modelMap, long id) {
		Products products = productsService.selectByPrimaryKey(id);
		if (products == null) {
			modelMap.put("result", false);
		}
		modelMap.put("products", products);
		return themeService.getTemplatePath("product/showProducts");
	}
}
