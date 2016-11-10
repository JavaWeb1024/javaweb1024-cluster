package com.dinghao.dao.manage.role;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dinghao.entity.manage.role.Role;
import com.dinghao.entity.vo.manage.rolevo.RoleVo;

@Repository
public interface RoleDao {
	int deleteByPrimaryKey(Long id);

	// 插入数据role表
	Long insertSelective(RoleVo roleVo);

	Role selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(RoleVo roleVo);

	public List<Role> getAllList(@Param("offset") long offset,
			@Param("rows") long rows);

	List<Role> getRolesByadminId(long adminId);

	// 获取角色信息列表
	List<Role> getRoles(RoleVo roleVo);

	// 获取角色信息列表 总数
	int getRolesCount(RoleVo roleVo);

	// 删除用户角色信息表dh_admin_role
	void deleteAdminRoleByRoleId(Long id);

	// 删除角色导航信息表 dh_menu_role
	void deleteMenuRoleByRoleId(Long id);

	// 保存导航信息表数据
	void insertMenuRoles(Map map);

	// 依据主键获取导航主键集合
	List<Long> findMenusByRoleId(Long id);
	/**
	 * 
	* @Title: deleteAllByPrimaryKey 
	* @Description: TODO(删除相关表数据) 
	* @param @param id    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void deleteAllByPrimaryKey(Long id);
}