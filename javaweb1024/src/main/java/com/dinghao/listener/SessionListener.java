package com.dinghao.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dinghao.redis.template.RedisClientTemplate;

/**
 * 
 * @ClassName: SessionListener
 * @Description: TODO(实现单点登录)
 * @author 紫寒1120(zihan1120@gmail.com)
 * @date 2016年6月16日 上午9:24:56
 *
 */
public class SessionListener implements HttpSessionListener {
	@Autowired
	static RedisClientTemplate redisClientTemplate;

	private Logger logger = LogManager.getLogger(SessionListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
	 * .HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		String sessionidKey = "spring:session:zihan:"+session.getId();
		if (redisClientTemplate == null) {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			redisClientTemplate = wac.getBean(RedisClientTemplate.class);
		}
		boolean hasKey = redisClientTemplate.exists(sessionidKey);
		if (!hasKey) {
			logger.info("创建了一个Session连接:[" + session.getId() + "]");
			setAllUserNumber(1);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
	 * .http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		if (getAllUserNumber() > 0) {
			logger.info("销毁了一个Session连接:[" + session.getId() + "]");
		}
		setAllUserNumber(-1);
	}

	private void setAllUserNumber(int n) {
		Long number = getAllUserNumber() + n;
		if (number >= 0) {
			logger.info("用户数：" + number);
			redisClientTemplate.set("ALLUSER_NUMBER", number.toString());
		}
	}

	/** 获取在线用户数量 */
	public static Long getAllUserNumber() {
		if (redisClientTemplate == null) {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			redisClientTemplate = wac.getBean(RedisClientTemplate.class);
		}
		String v = redisClientTemplate.get("ALLUSER_NUMBER");
		if (v != null) {
			return Long.valueOf(v);
		}
		return 0L;
	}
}
