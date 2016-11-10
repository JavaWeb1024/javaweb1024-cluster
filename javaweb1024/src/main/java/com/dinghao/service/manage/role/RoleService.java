package com.dinghao.service.manage.role;

import java.util.List;

import com.dinghao.entity.manage.menuroleauthorization.MenuRoleAuthorization;
import com.dinghao.entity.manage.role.MenuRole;
import com.dinghao.entity.manage.role.Role;
import com.dinghao.entity.vo.manage.PageVo;
import com.dinghao.entity.vo.manage.rolevo.RoleVo;

public interface RoleService {
	/**
	 * 
	 * 方法名: getAllRole
	 * <p/>
	 * 方法描述: 获取所有的角色信息
	 * <p/>
	 * 修改时间：2015年12月4日 下午4:56:08
	 * 
	 * @return 参数说明 返回类型 Role 返回类型
	 * @throws
	 */
	public List<Role> getAllRole();

	/**
	 * 
	 * 方法名: getRolesByadminId
	 * <p/>
	 * 方法描述: (依据AdminID获取roles)
	 * <p/>
	 * 修改时间：2015年12月7日 下午6:06:16
	 * 
	 * @param adminId
	 * @return 参数说明 返回类型 Role 返回类型
	 * @throws
	 */
	public List<Role> getRolesByadminId(long adminId);

	// 插入数据
	public Long insertSelective(RoleVo roleVo, Long[] menus);

	// 查询角色信息
	public PageVo<Role> getRoles(RoleVo roleVo);

	// 依据主键删除角色信息
	public void deleteByPrimaryKey(Long id);

	// 依据主键获取角色信息对象
	public Role selectByPrimaryKey(Long id);

	// 依据主键获取导航主键集合
	public List<Long> findMenusByRoleId(Long id);

	// 修改角色信息表
	public void updateByPrimaryKey(RoleVo roleVo, Long[] menus);

	// 删除权限详细表
	public void deleteMenuRoleByPrimaryKey(Long id);

	public MenuRole getMenuRoles(MenuRole menuRole);

	public List<MenuRoleAuthorization> getMenuRoleAuthorizationByAdminId(Long id);

}
