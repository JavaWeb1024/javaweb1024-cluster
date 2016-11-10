/*



 */

package com.dinghao.action.manage;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.dinghao.Principal;
import com.dinghao.constant.CommonConstant;
import com.dinghao.entity.manage.admin.Admin;
import com.dinghao.entity.vo.JsonVo;
import com.dinghao.exception.ValidateException;
import com.dinghao.redis.template.RedisClientTemplate;
import com.dinghao.service.ConfigService;
import com.dinghao.service.manage.AdminService;

/**
 * @author 所有action的父类
 * 
 */
@Controller
public class ManageBaseAction {

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	protected ConfigService configService;
	@Autowired
	protected AdminService adminService;
	@Autowired
	protected RedisClientTemplate redisClientTemplate;

	private String sessionKey = "admin:shrio_session:";

	@Value("${session.redis}")
	private Boolean sessionRedis;

	/**
	 * 参数校验
	 * 
	 * @param json
	 *            json数据Bean
	 * @throws ValidateException
	 */
	protected <T> void validate(JsonVo<T> json) throws ValidateException {
		if (json.getErrors().size() > 0) {
			json.setResult(false);
			throw new ValidateException(json.getErrors().values().toString());
		} else {
			json.setResult(true);
		}
	}

	/**
	 * 从session中获得管理员的信息
	 * 
	 * @param request
	 * @return
	 */
	protected Admin getAdmin(HttpServletRequest request) {
		Admin admin = null;
		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
		Session session = subject.getSession(false);
		// 采用redis管理session
		if (sessionRedis) {
			if (redisClientTemplate.exists((sessionKey + session.getId()).getBytes())){
				admin = (Admin) session.getAttribute(CommonConstant.ADMIN_SESSION.getValue());
			}
			
		} else {
			admin = (Admin) request.getSession().getAttribute(
					CommonConstant.ADMIN_SESSION.getValue());
		}
		if (admin == null) {
			Principal shiroUser = null;
			// 用户登录信息，允许记住用户。
			if (principal != null) {
				if (principal instanceof Principal) {
					shiroUser = (Principal) principal;
				}
			}
			if (shiroUser != null) {
				admin = adminService.getAdminByName(shiroUser.getUsername());
				admin.setPassword("");
				if (sessionRedis) {
					session.setAttribute(CommonConstant.ADMIN_SESSION.getValue(), admin);
				} else {
					HttpSession session1 = request.getSession(false);					
					session1.setAttribute(
							CommonConstant.ADMIN_SESSION.getValue(), admin);
				}
			}
		}
		return admin;
	}

	public void returnJson(HttpServletResponse response, JsonVo json)
			throws Exception {
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONString(json));
		out.close();
	}
}
