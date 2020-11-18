package com.edaochina.shopping.api.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.entity.user.SysMenu;
import com.edaochina.shopping.domain.vo.sys.SysMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 by 张志侃 Mapper 接口
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenuVO> sysMenuList();

    /**
     * 查询管理员的菜单
     *
     * @param roleId 管理员的角色id
     * @return 有权限的菜单
     */
    List<SysMenu> selectByRoleId(Integer roleId);

    /**
     * 查询管理员的菜单
     *
     * @param accountId 账号id
     * @return 有权限的菜单
     */
    List<SysMenu> selectByAccountId(Integer accountId);

    SysMenuVO queryById(@Param("id") String id);

    List<String> sysMenuIds();

    List<SysMenu> selectByRole(String role);
}
