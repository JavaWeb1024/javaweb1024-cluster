package com.dinghao.exception;

import org.apache.shiro.authc.AuthenticationException;

public class IncorrectCaptchaException extends AuthenticationException {

	/**
	 * @Fields serialVersionUID : (用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;
	public IncorrectCaptchaException(String msg) {
		super(msg);
	}

}
