/*



 */

package com.dinghao.exception;

/**
 * 
 * 目录没有发现异常
 * 
 * @author Herbert
 * 
 */
public class TemplateNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TemplateNotFoundException(String msg) {
		super(msg);
	}
}
