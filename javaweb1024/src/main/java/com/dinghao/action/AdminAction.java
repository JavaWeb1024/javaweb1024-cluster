package com.dinghao.action;

import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dinghao.constant.CommonConstant;
import com.dinghao.constant.SystemConstant;
import com.dinghao.entity.vo.JsonVo;
import com.dinghao.exception.IncorrectCaptchaException;
import com.dinghao.service.manage.AdminService;
import com.dinghao.util.HttpUtils;
import com.dinghao.util.PropertyUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;

/**
 * @author zihan 后台登陆（/manage/*的请求）没有成功，统一由此做处理
 */

@Controller
@RequestMapping("/admin")
public class AdminAction extends BaseAction {

	/**
	 * Kaptcha 验证码
	 */
	@Autowired
	private DefaultKaptcha captchaProducer;

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/login.jhtml", method = RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap modelMap,
			String requestUrlString) {
		modelMap.put("requestUrlString", requestUrlString);
		// 如果已登录,就不在跳转到登录页面
		if (request.getSession().getAttribute(
				CommonConstant.ADMIN_SESSION.getValue()) != null) {
			if (StringUtils.isBlank(requestUrlString))
				// 系统默认页面
				requestUrlString = "/manage/admin/myPage.jhtml";
			return "redirect:" + requestUrlString;
		}
		modelMap.put("config_v", PropertyUtils.getValue("dinghao.config_v"));
		modelMap.put("static_url", PropertyUtils.getValue("static.url"));
		modelMap.put("img_url", PropertyUtils.getValue("img.url"));
		return "/manage/html/login/index";
	}

	@RequestMapping(value = "/logout.jhtml", method = RequestMethod.GET)
	public String adminLogout(HttpServletRequest request, ModelMap modelMap) {
		request.getSession().removeAttribute(
				CommonConstant.ADMIN_SESSION.getValue());
		return "redirect:" + HttpUtils.getBasePath(request);
	}

	@RequestMapping(value = "/login.jhtml", method = RequestMethod.POST)
	public String adminLogin(HttpServletRequest request, ModelMap modelMap) {
		Boolean result = false;
		String massage = "";
		try {
			Object errorName = request
					.getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			result = false;
			String password = "登录有误!";
			if (errorName.toString().indexOf("IncorrectCaptchaException") > 0) {
				password += " 验证码错误  ";
			}
			if (errorName.toString().indexOf("UnknownAccountException") > 0
					|| errorName.toString().indexOf(
							"IncorrectCredentialsException") > 0) {
				password += " 用户名或密码错误 ";
			}
			massage = password;
		} catch (Exception e) {
			// 异常，重置验证码
			request.getSession().removeAttribute(
					com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			result = false;
			massage = "用户名或密码错误";
		}
		modelMap.put("config_v", PropertyUtils.getValue("dinghao.config_v"));
		modelMap.put("static_url", PropertyUtils.getValue("static.url"));
		modelMap.put("img_url", PropertyUtils.getValue("img.url"));
		modelMap.put("result", result);
		modelMap.put("massage", massage);
		return "/manage/html/login/index";
	}

	/**
	 * 生成验证码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "captcha.jhtml", method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
		request.getSession().setAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
		SecurityUtils
				.getSubject()
				.getSession()
				.setAttribute(
						com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY,
						capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}

	@RequestMapping("/unauthorized.jhtml")
	public String unauthorized(HttpServletRequest request,
			HttpServletResponse response) {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null
				&& requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "unauthorized");
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return "/manage/unauthorized";
	}

}
