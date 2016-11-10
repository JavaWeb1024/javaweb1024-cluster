package com.dinghao.constant;

public enum CommonConstant {
	ADMIN_SESSION("ADMIN_SESSION", "admin session的key"), TEMPLATE_ADMIN(
			"TEMPLATE_ADMIN", "前端 session的key");

	/**
	 * 名称
	 */
	private String value;

	/**
	 * 说明
	 */
	private String text;

	private CommonConstant(String value, String text) {
		this.setValue(value);
		this.setText(text);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
