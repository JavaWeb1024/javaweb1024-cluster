package com.dinghao.constant;

import com.dinghao.util.PropertyUtils;

/**
 * 系统常量
 * 
 * @author Herbert
 * 
 */
public class SystemConstant {

	/**
	 * 应用部署路径的KEY
	 */
	public static String DINGHAO_CMS_ROOT = PropertyUtils.getRoot();

	/**
	 * 上传文件夹
	 */
	public static String UPLOAD_FOLDER = "upload/photo";

	/**
	 * 备份文件夹
	 */
	public static String BACKUP_FOLDER = "/WEB-INF/backup";

	
	
	/**
	 * Session中的管理员Key(前端)
	 */
	public static final String TEMPLATE_ADMIN = "TEMPLATE_ADMIN";

	/**
	 * 头像URL 180x180
	 */
	public static final String FACE_URL = "";
	/**
	 * JS 与 CSS 去除缓存
	 */
	public static final String CONFIG_V = "config_v";

	/**
	 * 
	 */
	public static final String LANGUAGE = "language";

}
