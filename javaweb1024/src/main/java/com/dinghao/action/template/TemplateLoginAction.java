package com.dinghao.action.template;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TemplateLoginAction extends BaseAction {

	@Autowired
	TemplateAdminService templateAdminService;

	@SuppressWarnings("null")
	@RequestMapping(value = { "", "/login.jhtml" }, method = RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap modelMap, String requestUrlString) {
		TemplateAdmin templateAdmin = getTemplateAdmin();
		// 已登录状态
		if (templateAdmin != null) {
			return "redirect:/template";
		}
		// 没有登录
		modelMap.put("requestUrlString", requestUrlString);
		return themeService.getTemplatePath("login/index");
	}

	@RequestMapping(value = "/regist.jhtml", method = RequestMethod.GET)
	public String regist(HttpServletRequest request, ModelMap modelMap) {
		return themeService.getTemplatePath("login/regist");
	}

	@RequestMapping(value = { "/login.jhtml" }, method = RequestMethod.POST)
	public String loginPost(HttpServletRequest request, ModelMap modelMap, String requestUrlString, String username,
			String password, String captcha) {
		String kaptcha = (String) getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		boolean loginFlag = true;
		if (StringUtils.isNotBlank(kaptcha) && !kaptcha.equalsIgnoreCase(captcha)) {
			modelMap.put("massage", "验证码错误");
			loginFlag = false;
		}
		// 验证用户名密码
		TemplateAdmin templateAdmin = new TemplateAdmin();
		if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(password)) {
			templateAdmin = templateAdminService.selectByUserName(username);
			if (templateAdmin == null) {
				loginFlag = false;
				modelMap.put("massage", "账户或用户名密码不对");
			}
			if (loginFlag && !AuthUtils.getPassword(password).equals(templateAdmin.getPassword())) {
				loginFlag = false;
				modelMap.put("massage", "账户或用户名密码不对");
			}
		} else {
			loginFlag = false;
		}
		if (!loginFlag) {
			return themeService.getTemplatePath("login/index");
		}
		getSession().setAttribute(SystemConstant.TEMPLATE_ADMIN, templateAdmin);
		return "redirect:" + requestUrlString;

	}

	/**
	 * 
	 * @param 用户注册
	 *            &&&&
	 * @param modelMap
	 * @param username
	 * @param password
	 * @return Map
	 * @author cpc
	 */
	@RequestMapping(value = { "/regist.jhtml" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonVo<String> register(HttpServletRequest request, ModelMap modelMap, String username, String password) {
		// Map用来封装 insertTemplateAdmin()返回的参数
		JsonVo<String> result = new JsonVo<String>();
		TemplateAdminVo templateAdminVo = new TemplateAdminVo();
		// 注册逻辑
		result = templateAdminService.insertTemplateAdmin(username, password);
		if (result.isResult()) {
			TemplateAdmin templateAdmin = templateAdminService.selectByPrimaryKey(Long.parseLong(result.getT()));
			templateAdmin.setPassword("");
			getSession().setAttribute(SystemConstant.TEMPLATE_ADMIN, templateAdmin);
		}

		return result;
	}

}
