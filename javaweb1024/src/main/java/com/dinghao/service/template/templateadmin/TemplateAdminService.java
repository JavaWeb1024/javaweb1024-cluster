package com.dinghao.service.template.templateadmin;

import java.util.Map;

import com.dinghao.entity.template.templateadmin.TemplateAdmin;
import com.dinghao.entity.vo.JsonVo;
import com.dinghao.entity.vo.template.JqGridVo;
import com.dinghao.entity.vo.template.templateadmin.TemplateAdminVo;

public interface TemplateAdminService {
	/**
	 * 
	 * @Title: selectByPrimaryKey
	 * @Description: TODO(依据主键获取TemplateAdmin对象)
	 * @param @param id
	 * @param @return 设定文件
	 * @return Receipt 返回类型
	 * @throws
	 */
	public TemplateAdmin selectByPrimaryKey(Long id);

	/**
	 * 
	 * @Title: getReceiptDetails
	 * @Description: TODO(获取TemplateAdmin对象列表)
	 * @param @param receiptDetailVo
	 * @param @return 设定文件
	 * @return JqGridVo<TemplateAdmin> 返回类型
	 * @throws
	 */
	public JqGridVo<TemplateAdmin> getReceiptDetails(
			TemplateAdminVo receiptDetailVo);

	/**
	 * 
	* @Title: updateTemplateAdmin 
	* @Description: TODO(更新) 
	* @param @param templateAdminVo
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int updateTemplateAdmin(TemplateAdminVo templateAdminVo);

	/**
	 * 
	* @Title: insertTemplateAdmin 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param receiptDetailVo
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public JsonVo<String> insertTemplateAdmin(String username,String password);
	
	/**
	 * 
	* @Title: selectByUserName 
	* @Description: TODO(通过用户名获取对象) 
	* @param @param userName
	* @param @return    设定文件 
	* @return TemplateAdmin    返回类型 
	* @throws
	 */
	public TemplateAdmin selectByUserName(String userName);
}
