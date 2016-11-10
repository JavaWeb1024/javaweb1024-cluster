package com.dinghao.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dinghao.constant.SystemConstant;
import com.dinghao.entity.template.templateadmin.TemplateAdmin;

@Component
public class TemplateInterceptor extends HandlerInterceptorAdapter {
	protected final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		TemplateAdmin admin = (TemplateAdmin) SecurityUtils.getSubject().getSession()
				.getAttribute(SystemConstant.TEMPLATE_ADMIN);
		String redirectUrl = request.getQueryString() != null ? request
				.getRequestURI() + "?" + request.getQueryString() : request
				.getRequestURI();
		if (admin == null) {
			// 请求的URL
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path;
			response.sendRedirect(basePath + "/login.jhtml?requestUrlString="
					+ redirectUrl);
		} else {
			return true;
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
