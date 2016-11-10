package com.dinghao.service.template.templateadmin.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinghao.dao.template.templateadmin.TemplateAdminDao;
import com.dinghao.entity.template.templateadmin.TemplateAdmin;
import com.dinghao.entity.vo.JsonVo;
import com.dinghao.entity.vo.template.JqGridVo;
import com.dinghao.entity.vo.template.templateadmin.TemplateAdminVo;
import com.dinghao.service.template.templateadmin.TemplateAdminService;
import com.dinghao.util.AuthUtils;

@Service
public class TemplateAdminServiceImp implements TemplateAdminService {
	@Autowired
	TemplateAdminDao templateAdminDao;

	@Override
	public TemplateAdmin selectByPrimaryKey(Long id) {
		return templateAdminDao.selectByPrimaryKey(id);
	}

	@Override
	public JqGridVo<TemplateAdmin> getReceiptDetails(
			TemplateAdminVo receiptDetailVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateTemplateAdmin(TemplateAdminVo templateAdminVo) {

		return templateAdminDao.updateByPrimaryKeySelective(templateAdminVo);
	}

	@Override
	public JsonVo<String> insertTemplateAdmin(String username, String password) {
		// JsonVo用来封装状态信息
		JsonVo<String> jsonVo = new JsonVo<String>();
		// 检测账号是否被占用
		TemplateAdmin templateadmin = templateAdminDao.selectByUserName(username);
		if (templateadmin != null) {
			jsonVo.setResult(false);
			jsonVo.setMsg("用户名已存在！");
		} else {
			if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
				// 这个实体类用来封装要注册的账户名和密码
				TemplateAdminVo templateAdminVo = new TemplateAdminVo();
				String md5_password = AuthUtils.getPassword(password);
				templateAdminVo.setUsername(username);
				templateAdminVo.setPassword(md5_password);
				templateAdminVo.setCreateDate(new Date());
				int upNum = templateAdminDao.insertSelective(templateAdminVo);
				if (upNum > 0) {
					jsonVo.setT(templateAdminVo.getId().toString());
					jsonVo.setResult(true);
					jsonVo.setMsg("注册成功!");
				}
			}
		}
		return jsonVo;
	}

	@Override
	public TemplateAdmin selectByUserName(String userName) {
		return templateAdminDao.selectByUserName(userName);
	}

}
