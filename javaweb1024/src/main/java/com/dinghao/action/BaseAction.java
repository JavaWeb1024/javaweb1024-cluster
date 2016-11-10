package com.dinghao.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.dinghao.constant.SystemConstant;
import com.dinghao.entity.template.templateadmin.TemplateAdmin;
import com.dinghao.exception.DHBizException;
import com.dinghao.redis.template.RedisClientTemplate;
import com.dinghao.service.TemplateService;

/**
 * 
 * @author Herbert
 * 
 */
public class BaseAction {

	public static String CONTENT_TYPE_JPG = "image/jpeg";
	public static String CONTENT_TYPE_PJPEG = "image/pjpeg";
	public static String CONTENT_TYPE_X_PNG = "image/x-png";
	public static String CONTENT_TYPE_JPEG = "image/jpeg";
	public static String CONTENT_TYPE_BMP = "image/bmp";
	public static String CONTENT_TYPE_PNG = "image/png";
	public static String CONTENT_TYPE_TXT = "text/plain";
	public static String CONTENT_TYPE_DOC = "application/msword";
	public static String CONTENT_TYPE_PDF = "application/pdf";
	public static String FILE_PNG = "PNG";
	public static String FILE_JPEG = "JPEG";
	public static String FILE_JPG = "JPG";
	public static String FILE_GRF = "GRF";
	public static String FILE_BMP = "BMP";

	@Autowired
	protected TemplateService themeService;
	@Autowired
	protected RedisClientTemplate redisClientTemplate;

	protected final Logger logger = Logger.getLogger(this.getClass());

	public String checkFileReasonable(MultipartFile file) {
		if (null == file) {
			throw new DHBizException("文件不存在");
		}

		String errMsg = "";
		List<String> contenTypeList = new ArrayList<String>();
		contenTypeList.add(CONTENT_TYPE_JPG);
		contenTypeList.add(CONTENT_TYPE_JPEG);
		contenTypeList.add(CONTENT_TYPE_PNG);
		contenTypeList.add(CONTENT_TYPE_BMP);
		contenTypeList.add(CONTENT_TYPE_PJPEG);
		contenTypeList.add(CONTENT_TYPE_X_PNG);

		List<String> endFileName = new ArrayList<String>();
		endFileName.add(FILE_JPEG);
		endFileName.add(FILE_JPG);
		endFileName.add(FILE_PNG);
		endFileName.add(FILE_BMP);
		boolean isTrue = false;
		for (String fileName : endFileName) {
			if (file.getOriginalFilename().toUpperCase().endsWith(fileName)) {
				isTrue = true;
			}
		}
		if (!isTrue) {
			errMsg = "上传文件不符合类型";
			return errMsg;
		}
		isTrue = false;
		for (String type : contenTypeList) {
			if (file.getContentType().equalsIgnoreCase(type)) {
				isTrue = true;
			}
		}
		if (!isTrue) {
			errMsg = "上传文件不符合类型";
			return errMsg;
		}
		if (file.getSize() > 2 * 1024 * 1024) {
			errMsg = "最大允许导入2M的文件";
		}
		return errMsg;
	}

	protected TemplateAdmin getTemplateAdmin() {
		TemplateAdmin templateAdmin = (TemplateAdmin) SecurityUtils
				.getSubject().getSession().getAttribute(SystemConstant.TEMPLATE_ADMIN);
		return templateAdmin;

	}

	protected Session getSession() {
		Session session = SecurityUtils.getSubject().getSession();
		return session;

	}

	protected void removeSession() {
		SecurityUtils.getSubject().getSession().removeAttribute(SystemConstant.TEMPLATE_ADMIN);
		// 在线人数-1
		Long number = Long.parseLong(redisClientTemplate.get("ALLUSER_NUMBER"));
		if(number<1){
			number=1l;
		}else {
			number--;
		}
		redisClientTemplate.set("ALLUSER_NUMBER", number.toString());
	}
}
