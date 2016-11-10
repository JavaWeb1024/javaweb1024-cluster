package com.dinghao.service.manage.menu;

import java.util.List;
import java.util.Set;

import com.dinghao.entity.manage.menu.Menu;
import com.dinghao.entity.vo.manage.PageVo;
import com.dinghao.entity.vo.manage.menuvo.MenuVo;

public interface MenuService {
	/**
	 * 
	 * 方法名: insertSelective
	 * <p/>
	 * 方法描述: (新增导航栏目)
	 * <p/>
	 * 修改时间：2015年12月8日 下午5:44:23
	 * 
	 * @param record
	 * @return 参数说明 返回类型 int 返回类型
	 * @throws
	 */
	public int insertSelective(MenuVo record);

	// 更新
	public int updateByPrimaryKey(MenuVo record);

	// 删除
	public int deleteByPrimaryKey(Long id);

	// 获取父菜单项
	public PageVo<Menu> findParentMenu(MenuVo menuVo);

	// 获取子栏目
	public List<Menu> findChildrenMenu(Integer id, Long adminId);

	// 依据主键获取menu对象
	public Menu selectByPrimaryKey(MenuVo menuVo);

	/**
	 * 
	 * 方法名: findParentMenuByAdminId<br/>
	 * 方法描述: 依据用户主键获取菜单父节点<br/>
	 * 修改时间：2015年12月11日 上午11:24:17
	 * 
	 * @param id
	 * @return 参数说明 返回类型 List<Menu> 返回类型
	 * @throws
	 */
	public List<Menu> findParentMenuByAdminId(Integer id);

	public List<Menu> findGrandParentsMenuByAdminId(Set<Long> grandprandId);

	PageVo<Menu> findParentMenuPage(MenuVo menuVo);

	/**
	 * 
	 * @Title: findMenuByAdminId
	 * @Description: TODO(获取权限)
	 * @param @param intValue
	 * @param @return 设定文件
	 * @return List<Menu> 返回类型
	 * @throws
	 */
	public List<Menu> findMenuByAdminId(Long intValue);

}
