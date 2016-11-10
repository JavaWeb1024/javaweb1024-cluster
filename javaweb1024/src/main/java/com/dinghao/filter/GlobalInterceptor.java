package com.dinghao.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dinghao.Principal;
import com.dinghao.constant.CommonConstant;
import com.dinghao.constant.ConfigConstant;
import com.dinghao.entity.manage.admin.Admin;
import com.dinghao.entity.manage.menu.Menu;
import com.dinghao.entity.vo.manage.menuvo.MenuVo;
import com.dinghao.service.ConfigService;
import com.dinghao.service.manage.AdminService;
import com.dinghao.service.manage.menu.MenuService;
import com.dinghao.util.HttpUtils;
import com.dinghao.util.PropertyUtils;

/**
 * @author ZiHan
 * 
 */
@Component
public class GlobalInterceptor implements HandlerInterceptor {

	@Autowired
	private ConfigService configService;
	@Autowired
	protected AdminService adminService;
	@Autowired
	private MenuService menuService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (null == modelAndView) {
			return;
		}
		// 系统配置参数
		String basePath = HttpUtils.getBasePath(request);
		modelAndView.addObject("BASE_PATH", basePath);
		modelAndView.addObject("UPLOAD_BASE_PATH", basePath + "/upload");
		modelAndView.addObject("config_v",
				PropertyUtils.getValue("dinghao.config_v"));
		modelAndView.addObject("static_url",
				PropertyUtils.getValue("static.url"));

		MDC.put("ip", HttpUtils.getIp(request));
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
