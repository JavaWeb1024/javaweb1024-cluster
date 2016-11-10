package com.dinghao.tag;

import static freemarker.template.ObjectWrapper.BEANS_WRAPPER;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinghao.entity.manage.menu.Menu;
import com.dinghao.exception.FolderNotFoundException;
import com.dinghao.plugin.TagPlugin;
import com.dinghao.service.manage.menu.MenuService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * 类名称：MenuChildrenListTag<br/>
 * 类描述：菜单栏子项抓取<br/>
 * 创建人：紫寒1120 <br/>
 * 创建时间：2015年12月9日 下午1:51:17<br/>
 * 修改人：紫寒1120 <br/>
 * 修改时间：2015年12月9日 下午1:51:17 <br/>
 * 修改备注：<br/>
 * 
 * @version 1.0
 *
 */
@Service
public class MenuChildrenListTag extends TagPlugin {
	@Autowired
	MenuService menuService;

	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// 获取页面的参数
		Integer id = Integer.parseInt(params.get("id").toString());
		List<Menu> menulList = menuService.findChildrenMenu(id,1l);
		env.setVariable("tag_menu_children_list", BEANS_WRAPPER.wrap(menulList));
		body.render(env.getOut());
	}

}
