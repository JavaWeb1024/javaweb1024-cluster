package com.dinghao.dao.manage.menu;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dinghao.entity.manage.menu.Menu;
import com.dinghao.entity.vo.manage.menuvo.MenuVo;

@Repository
public interface MenuDao {
	// 删除
	int deleteByPrimaryKey(Long id);

	/**
	 * 
	 * 方法名: insertSelective
	 * <p/>
	 * 方法描述: (新增导航栏)
	 * <p/>
	 * 修改时间：2015年12月8日 下午5:29:37
	 * 
	 * @param record
	 * @return 参数说明 返回类型 int 返回类型
	 * @throws
	 */
	int insertSelective(MenuVo record);

	Menu selectByPrimaryKey(MenuVo menuVo);

	int updateByPrimaryKey(MenuVo record);

	// 获取父节点
	List<Menu> findParentMenu(MenuVo menuVo);

	// 获取父节点分页
	List<Menu> findParentMenuPage(MenuVo menuVo);
	// 父节点个数
	int findParentMenuCount();

	// 依据父节点获取所有子节点
	List<Menu> findChildrenMenu(@Param("id")Integer id);

	// 删除所有子节点
	void deleteByParentId(Long id);

	// 依据用户ID获取所有父节点
	List<Menu> findParentMenuByAdminId(@Param("id") Integer id);
	
	// 依据用户ID获取所有曾祖节点
	List<Menu> findGrandParentsMenuByAdminId(List<Long> list);

	void deleteMenuRoleByMenuId(Long id);

	// 依据父节点获取所有有权限的子节点
	List<Menu> findChildrenMenuByAdminId(@Param("id")Integer id,@Param("adminId") Long adminId);

	List<Menu> findMenuByAdminId(@Param("id") Long _id);
}