package com.dinghao.action.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.attribute.HashAttributeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dinghao.action.BaseAction;
import com.dinghao.entity.template.products.Products;
import com.dinghao.entity.template.templateadmin.TemplateAdmin;
import com.dinghao.entity.vo.template.JqGridVo;
import com.dinghao.entity.vo.template.products.ProductsVo;
import com.dinghao.service.template.products.ProductsService;
import com.dinghao.service.template.templateadmin.TemplateAdminService;

@Controller
@RequestMapping("/apk")
public class ApkAction extends BaseAction {
	@Autowired
	TemplateAdminService templateAdminService;
	@Resource
	ProductsService productsService;

	@RequestMapping(value = { "", "/login.jhtml" }, method = RequestMethod.POST)
	@ResponseBody
	public TemplateAdmin index(@RequestBody String message) {
		JSONObject jsonObject = JSONObject.parseObject(message);
		// 登录名
		String username = StringUtils.trimToEmpty(jsonObject.getString("username"));
		// 登录密码
		String password = StringUtils.trimToEmpty(jsonObject.getString("password"));
		TemplateAdmin templateAdmin = new TemplateAdmin();
		if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(password)) {
			templateAdmin = templateAdminService.selectByUserName(username);
		} else {
			templateAdmin.setCode("1");
			templateAdmin.setResultMsg("用户名或密码为空!");
		}
		// 校验密码的正确性
		if (!password.equals(templateAdmin.getPassword())) {
			templateAdmin.setCode("1");
			templateAdmin.setResultMsg("用户名或密码错误!");
		}
		// 去除密码
		templateAdmin.setPassword("");
		templateAdmin.setCode("0");
		return templateAdmin;
	}

	/**
	 * 依据商户信息获取商品列表
	 * 
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/products_list.jhtml", method = RequestMethod.POST)
	@ResponseBody
	public Map productsList(@RequestBody String message) {
		JSONObject jsonObject = JSONObject.parseObject(message);
		List<Products> products = new ArrayList<Products>();
		Map map = new HashMap();
		// 监测商户ID
		if (StringUtils.isBlank(StringUtils.trimToEmpty(jsonObject.getString("tempAdminId")))) {
			map.put("code", "1");
			map.put("resultMsg", "商户主键不能为空!");
		} else {
			// 商户ID
			Long tempAdminId = Long.parseLong(StringUtils.trimToEmpty(jsonObject.getString("tempAdminId")));
			// 页码
			int pageNum = Integer.parseInt(StringUtils.trimToEmpty(jsonObject.getString("pageNum")));
			// 每页显示数量
			int rows = Integer.parseInt(StringUtils.trimToEmpty(jsonObject.getString("rows")));
			ProductsVo productsVo = new ProductsVo();
			productsVo.setTempAdminId(tempAdminId);
			productsVo.setPageNum(pageNum);
			productsVo.setRows(rows);
			JqGridVo<Products> jqGridVo = productsService.selectProducts(productsVo);
			products = jqGridVo.getList();
			map.put("products", products);
			map.put("code", "0");
			map.put("count", jqGridVo.getRecords());
			map.put("resultMsg", "查询成功");
		}
		return map;
	}

	@RequestMapping(value = "/product.jhtml", method = RequestMethod.POST)
	@ResponseBody
	public Map product(@RequestBody String message) {
		JSONObject jsonObject = JSONObject.parseObject(message);
		List<Products> products = new ArrayList<Products>();
		Map map = new HashMap();
		// 监测商户ID
		if (StringUtils.isBlank(StringUtils.trimToEmpty(jsonObject.getString("tempAdminId")))) {
			map.put("code", "1");
			map.put("resultMsg", "商户主键不能为空!");
		} else {
		// 商户ID
		Long tempAdminId = Long.parseLong(StringUtils.trimToEmpty(jsonObject.getString("tempAdminId")));
		// 主键
		Long id = Long.parseLong(StringUtils.trimToEmpty(jsonObject.getString("id")));
		ProductsVo productsVo = new ProductsVo();
		productsVo.setTempAdminId(tempAdminId);
		productsVo.setId(id);
		productsVo.setPageNum(1);
		productsVo.setRows(10);
		products = productsService.selectProducts(productsVo).getList();
		map.put("products", products.get(0));
		map.put("code", "0");
		map.put("resultMsg", "查询成功");
		}
		return map;
	}

}
