package com.dinghao.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dinghao.constant.ConfigConstant;
import com.dinghao.constant.SystemConstant;
import com.dinghao.exception.FolderNotFoundException;
import com.dinghao.exception.TemplateNotFoundException;

/**
 * 模板工具类
 * 
 * @author Herbert
 * 
 */
@Service
public class TemplateService {

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ConfigService configService;

	/**
	 * @return
	 */
	public String get404() {
		return this.getTemplatePath("404");
	}

	/**
	 * @return
	 */
	public String get500() {
		return this.getTemplatePath("500");
	}
	/**
	 * 得到当前请求需要渲染的模板相对路径
	 * 
	 * @param theme
	 * @return
	 */
	public String getTemplatePath(String template) {
		return "/template/"
				+ configService.getStringByKey(ConfigConstant.DINGHAO_TEMPLATE)
				+ "/" + template;
	}

	/**
	 * 模板物理地址是否存在
	 * 
	 * @param theme
	 * @return
	 */
	@Cacheable("default")
	public Boolean isExist(String theme) {
		String themePath = "/WEB-INF/static/template/"
				+ configService.getStringByKey(ConfigConstant.DINGHAO_TEMPLATE)
				+ "/" + theme + ".html";
		File file = new File(SystemConstant.DINGHAO_CMS_ROOT + themePath);
		if (file.exists()) {
			logger.info("尝试使用模板：" + themePath+"【存在】");
			return true;
		} else {
			logger.info("尝试使用模板：" + themePath+"【不存在】");
			return false;
		}
	}
}
