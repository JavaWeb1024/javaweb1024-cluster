package com.dinghao.action.template;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dinghao.action.BaseAction;
import com.dinghao.constant.SystemConstant;
import com.dinghao.entity.template.templateadmin.TemplateAdmin;
import com.dinghao.entity.vo.JsonVo;
import com.dinghao.entity.vo.template.templateadmin.TemplateAdminVo;
import com.dinghao.service.template.templateadmin.TemplateAdminService;
import com.dinghao.util.AuthUtils;

@Controller
@RequestMapping("/template")
public class TemplateAction extends BaseAction {
	
	@Resource
	TemplateAdminService templateAdminService;
	
	@RequestMapping(value = { "", "/index.jhtml" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap modelMap,
			String requestUrlString) {
		modelMap.put("name", getTemplateAdmin().getUsername());
		return themeService.getTemplatePath("index/index");
	}
	
	@RequestMapping(value = { "login/changePassword.jhtml" }, method = RequestMethod.GET)
	public String changePassword(HttpServletRequest request, ModelMap modelMap) {
		return themeService.getTemplatePath("login/changePassword");
	}
	
	@RequestMapping(value = "/logout.jhtml", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, ModelMap modelMap,
			String requestUrlString) {
		removeSession();
		return themeService.getTemplatePath("login/index");
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = { "login/changePassword.jhtml" }, method = RequestMethod.POST)
	public JsonVo<String> changePassword(HttpServletRequest request,String old_password,String new_password,
			String confirm_password) {
		JsonVo<String> JsonVo = new JsonVo<String>();	
		Long id=getTemplateAdmin().getId();
		TemplateAdmin templateAdmin=templateAdminService.selectByPrimaryKey(id);
		if("".equals(new_password)){
			JsonVo.setResult(false);
			JsonVo.setMsg("密码不能为空");
			return JsonVo;
		}
		
		if(templateAdmin.getPassword().equals(AuthUtils.getPassword(old_password)) && new_password.equals(confirm_password)){	
			TemplateAdminVo templateAdminVo=new TemplateAdminVo();
			templateAdminVo.setId(id);
			templateAdminVo.setPassword(AuthUtils.getPassword(new_password));
			int a = templateAdminService.updateTemplateAdmin(templateAdminVo);
			if(a == 1){
				JsonVo.setResult(true);
				JsonVo.setMsg("密码修改成功!");
			}else{
				JsonVo.setResult(false);
				JsonVo.setMsg("操作结束,请重新操作");
			}
		}else{
				JsonVo.setResult(false);
				JsonVo.setMsg("操作有误,请重新操作");
		}
		return JsonVo;

	}
	
	
	@RequestMapping(value = {"login/exit.jhtml" }, method = RequestMethod.GET)
	public String exit (HttpServletRequest request) {
		TemplateAdmin templateAdmin = getTemplateAdmin();
		if(templateAdmin != null){
			getSession().removeAttribute(SystemConstant.TEMPLATE_ADMIN);
		}
		
		return themeService.getTemplatePath("login/index");
	}
	
}


