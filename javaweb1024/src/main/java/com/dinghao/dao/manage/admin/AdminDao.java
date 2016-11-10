package com.dinghao.dao.manage.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dinghao.entity.manage.admin.Admin;
import com.dinghao.entity.vo.manage.adminvo.AdminVo;

@Repository
public interface AdminDao {
	int deleteByPrimaryKey(Long id);

	/**
	 * 
	 * 方法名: insert
	 * <p>
	 * 方法描述: (新增管理员)
	 * <p>
	 * 修改时间：2015年12月4日 下午2:37:18
	 * 
	 * @param adminVo
	 * @return 参数说明 返回类型 int 返回类型
	 * @throws
	 */
	public long insert(@Param("adminVo") AdminVo adminVo);

	int insertSelective(Admin record);

	/**
	 * 
	 * 方法名: getAdminByName
	 * <p/>
	 * 方法描述: 依据用户名获取登陆者实体对象
	 * <p/>
	 * 修改时间：2015年12月4日 下午2:21:08
	 * 
	 * @param name
	 * @return 参数说明 返回类型 Admin 返回类型
	 * @throws
	 */
	public Admin getAdminByName(@Param("name") String name);

	Admin selectByPrimaryKey(Long id);

	/**
	 * 
	 * 方法名: updateByPrimaryKeySelective
	 * <p/>
	 * 方法描述: (修改用户信息)
	 * <p/>
	 * 修改时间：2015年12月8日 上午9:22:50
	 * 
	 * @param record
	 * @return 参数说明 返回类型 int 返回类型
	 * @throws
	 */
	int updateByPrimaryKeySelective(AdminVo record);
	/**
	 * 获取所有管理员列表
	 * 
	 * @param offset
	 * @param rows
	 * @return List<Admin>
	 * 
	 */
	public List<Admin> getAllList(@Param("offset") long offset,
			@Param("rows") long rows);

	/**
	 * 获取所有管理员的数量
	 * 
	 * @return Integer
	 * 
	 */
	public int getAllListCount();

	/**
	 * 
	 * 方法名: addAdminRoles
	 * <p/>
	 * 方法描述: (添加用户角色中间表数据)
	 * <p/>
	 * 修改时间：2015年12月7日 下午2:32:34
	 * 
	 * @param id
	 * @param long1
	 *            参数说明 返回类型 void 返回类型
	 * @throws
	 */
	public void addAdminRoles(@Param("admins") Long id,
			@Param("roles") Long long1);

	/**
	 * 
	 * 方法名: deleteAdminRoleByAdminId
	 * <p/>
	 * 方法描述: (依据adminid删除dh_admin_role表中相关数据)
	 * <p/>
	 * 修改时间：2015年12月8日 上午10:07:17
	 * 
	 * @param id
	 * @return 参数说明 返回类型 int 返回类型
	 * @throws
	 */
	public int deleteAdminRoleByAdminId(Long id);

	// 依据主键修改密码
	void updatePasswordByAmdinId(@Param("id")Long id,@Param("password")String password);
}