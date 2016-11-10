/*



 */

package com.dinghao.entity.vo;

import java.io.Serializable;
import java.util.HashMap;

import com.dinghao.exception.ValidateException;

public class JsonVo<T> implements Serializable {
	/**
	 * @Fields serialVersionUID : (用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * 结果1
	 */
	private boolean result;	
	/**
	 * 结果2
	 */
	private boolean success;
	/**
	 * 成功的消息
	 */
	private String msg;

	/**
	 * 具体每个输入错误的消息
	 */
	private HashMap<String, String> errors = new HashMap<String, String>();

	/**
	 * 返回的数据
	 */
	private T t;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public HashMap<String, String> getErrors() {
		return errors;
	}

	public void setErrors(HashMap<String, String> errors) {
		this.errors = errors;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public void check() throws ValidateException {
		if (this.getErrors().size() > 0) {
			this.setResult(false);
			throw new ValidateException("有错误发生");
		} else {
			this.setResult(true);
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
