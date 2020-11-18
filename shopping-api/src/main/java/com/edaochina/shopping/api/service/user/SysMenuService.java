package com.edaochina.shopping.api.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edaochina.shopping.domain.entity.user.SysMenu;
import com.edaochina.shopping.domain.vo.sys.SysMenuInfo;
import com.edaochina.shopping.domain.vo.user.MenuVO;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 菜单表 by 张志侃 服务类
 * </p>
 *
 * @author zzk
 * @since 2018-12-26
 */
public interface SysMenuService extends IService<SysMenu> {
    MenuVO query(String id);

    Collection<SysMenuInfo> menuList();

    List<String> sysMenuIds();

    List<SysMenu> selectByRoleId(Integer roleId);

    List<SysMenu> selectByAccountId(Integer accountId);

    List<SysMenu> selectByRole(String role);
}
