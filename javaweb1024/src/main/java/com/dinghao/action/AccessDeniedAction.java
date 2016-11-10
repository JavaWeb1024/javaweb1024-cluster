package com.dinghao.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/access_denied")
public class AccessDeniedAction extends BaseAction {
	@RequestMapping(value = "/404.jhtml", method = RequestMethod.GET)
	public String accessDeniedFor404(HttpServletRequest request, ModelMap modelMap) {
		return themeService.get404();
	}
	
	@RequestMapping(value = "/500.jhtml", method = RequestMethod.GET)
	public String accessDeniedFor500(HttpServletRequest request, ModelMap modelMap) {
		return themeService.get500();
	}
}
