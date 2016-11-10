package com.dinghao.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dinghao.constant.CommonConstant;
import com.dinghao.constant.SystemConstant;
import com.dinghao.entity.manage.admin.Admin;

/**
 * 
 * 管理过滤器
 * 
 * @author Herbert
 * 
 */
public class ManageFilter implements Filter {

	protected final Logger logger = Logger.getLogger(this.getClass());

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Admin admin = (Admin) request.getSession().getAttribute(
				CommonConstant.ADMIN_SESSION.getValue());
		// 请求的URL
		String redirectUrl = request.getQueryString() != null ? request
				.getRequestURI() + "?" + request.getQueryString() : request
				.getRequestURI();
		if (admin == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path;
			response.sendRedirect(basePath
					+ "/admin/login.jhtml?requestUrlString="
					+ redirectUrl);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
