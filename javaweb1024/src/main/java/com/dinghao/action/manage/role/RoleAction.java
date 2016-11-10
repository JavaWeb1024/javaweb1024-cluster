package com.dinghao.action.manage.role;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dinghao.action.manage.ManageBaseAction;
import com.dinghao.entity.manage.menuroleauthorization.MenuRoleAuthorization;
import com.dinghao.entity.manage.role.MenuRole;
import com.dinghao.entity.manage.role.Role;
import com.dinghao.entity.vo.JsonVo;
import com.dinghao.entity.vo.manage.menuvo.MenuVo;
import com.dinghao.entity.vo.manage.rolevo.RoleVo;
import com.dinghao.exception.FolderNotFoundException;
import com.dinghao.service.manage.menu.MenuService;
import com.dinghao.service.manage.menuroleauthorization.MenuRoleAuthorizationService;
import com.dinghao.service.manage.role.RoleService;

@Controller
@RequestMapping("/manage/role")
public class RoleAction extends ManageBaseAction {
	@Autowired
	MenuService menuService;
	@Autowired
	RoleService roleService;
	@Autowired
	MenuRoleAuthorizationService menuRoleAuthorizationService;

	/**
	 * 
	 * 方法名: index<br/>
	 * 方法描述: (角色信息)<br/>
	 * 修改时间：2015年12月9日 上午9:42:43
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws FolderNotFoundException
	 *             参数说明 返回类型 String 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/add.jhtml", method = RequestMethod.GET)
	public String add(HttpServletRequest request, ModelMap modelMap)
			throws FolderNotFoundException {
		// 获取导航信息表
		MenuVo menuVo = new MenuVo();
		menuVo.setOffset(0);
		menuVo.setRows(Integer.MAX_VALUE);
		menuVo.setMenuType(1l);
		modelMap.put("parentList", menuService.findParentMenu(menuVo));
		return "/manage/html/role/add";
	}

	/**
	 * 
	 * 方法名: list<br/>
	 * 方法描述: 角色信息列表<br/>
	 * 修改时间：2015年12月10日 上午10:27:09
	 * 
	 * @param request
	 * @param modelMap
	 * @param roleVo
	 * @return
	 * @throws FolderNotFoundException
	 *             参数说明 返回类型 String 返回类型
	 * @throws
	 */

	@RequestMapping(value = "/list.jhtml", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap modelMap,
			RoleVo roleVo) throws FolderNotFoundException {
		modelMap.put("rolesList", roleService.getRoles(roleVo));
		return "/manage/html/role/list";
	}

	@RequestMapping(value = "/add_authorization.jhtml", method = RequestMethod.GET)
	public String addAuthorization(HttpServletRequest request,
			ModelMap modelMap, Long roles, Long menus) {
		List<MenuRoleAuthorization> menuRoleAuthorizations = menuRoleAuthorizationService
				.selectByStatementIds(roles, menus);
		modelMap.put("roles", roles);
		modelMap.put("menus", menus);
		modelMap.put("menuRoleAuthorizations", menuRoleAuthorizations);
		return "/manage/html/role/add_authorization";
	}

	@ResponseBody
	@RequestMapping(value = "/add_authorization_from.jhtml", method = RequestMethod.POST)
	public JsonVo<String> addAuthorizationForm(HttpServletRequest request,
			ModelMap modelMap, MenuRoleAuthorization menuRoleAuthorization,
			MenuRole menuRole) {
		JsonVo<String> json = new JsonVo<String>();
		Long i = menuRoleAuthorizationService.insertSelective(
				menuRoleAuthorization, menuRole);
		if (i > 0) {
			json.setSuccess(true);
			json.setMsg(menuRoleAuthorization.getId().toString());
			json.setT(i.toString());
		} else {
			json.setSuccess(false);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/delete_authorization.jhtml", method = RequestMethod.POST)
	public JsonVo<String> deleteAuthorization(HttpServletRequest request,
			ModelMap modelMap, Long id) {
		JsonVo<String> json = new JsonVo<String>();
		roleService.deleteMenuRoleByPrimaryKey(id);
		json.setSuccess(true);
		json.setMsg("操作成功");
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/addJson.jhtml", method = RequestMethod.POST)
	public JsonVo<String> updateAdmin(RoleVo roleVo, HttpServletRequest request) {
		JsonVo<String> json = new JsonVo<String>();
		String checkResult = checkData(roleVo);
		// 校验数据
		if (StringUtils.isNotBlank(checkResult)) {
			json.setResult(false);
			json.setMsg(checkResult);
		}
		try {
			// 检测校验结果
			validate(json);
			long roleid = roleService.insertSelective(roleVo, null);
			json.setResult(true);
			json.setT(String.valueOf(roleid));
		} catch (Exception e) {
			json.setResult(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}

	private String checkData(RoleVo roleVo) {
		StringBuffer stringBuffer = new StringBuffer();
		if (StringUtils.isBlank(roleVo.getName())) {
			stringBuffer.append("角色名称不能为空");
		}
		return stringBuffer.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/deleteJson.jhtml", method = RequestMethod.POST)
	public JsonVo<String> delete(
			@RequestParam(value = "id", defaultValue = "1") Long id,
			HttpServletRequest request) {
		JsonVo<String> json = new JsonVo<String>();
		// 校验数据 不允许删除内置数据
		if (id == null || id == 1l) {
			json.setResult(false);
			json.setMsg("操作有误，请重新操作");
		}
		try {
			// 检测校验结果
			validate(json);
			roleService.deleteByPrimaryKey(id);
			json.setResult(true);
			json.setMsg("操作成功");
		} catch (Exception e) {
			json.setResult(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}

	// 修改角色
	@RequestMapping(value = "/update.jhtml", method = RequestMethod.GET)
	public String update(HttpServletRequest request, ModelMap modelMap, Long id) {
		// 获取role对象
		Role role = roleService.selectByPrimaryKey(id);
		modelMap.put("roleOnly", role);
		modelMap.put("menuList", roleService.findMenusByRoleId(id));
		// 获取导航信息表
		MenuVo menuVo = new MenuVo();
		menuVo.setOffset(0);
		menuVo.setRows(Integer.MAX_VALUE);
		menuVo.setMenuType(1l);
		modelMap.put("parentList", menuService.findParentMenu(menuVo));
		return "/manage/html/role/update";
	}

	// 删除角色节点数据
	@ResponseBody
	@RequestMapping(value = "/updateJson.jhtml", method = RequestMethod.POST)
	public JsonVo<String> update(RoleVo roleVo, HttpServletRequest request,Long role_id,Long menu_id ) {
		JsonVo<String> json = new JsonVo<String>();
		try {
			menuRoleAuthorizationService.deleteByPrimaryKey(role_id, menu_id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}
}
