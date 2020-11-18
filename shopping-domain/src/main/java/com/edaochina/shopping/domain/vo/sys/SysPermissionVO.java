package com.edaochina.shopping.domain.vo.sys;

import com.edaochina.shopping.domain.entity.user.SysMenu;

import java.util.List;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 后台权限视图类
 * @since : 11:58
 */
public class SysPermissionVO {

    private List<SysMenu> menuList;

    private List<String> buttonAliasList;

    public static SysPermissionVO build() {
        return new SysPermissionVO();
    }

    public List<SysMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenu> menuList) {
        this.menuList = menuList;
    }

    public List<String> getButtonAliasList() {
        return buttonAliasList;
    }

    public void setButtonAliasList(List<String> buttonAliasList) {
        this.buttonAliasList = buttonAliasList;
    }

    @Override
    public String toString() {
        return "SysPermissionVO{" +
                "menuList=" + menuList +
                ", buttonAliasList=" + buttonAliasList +
                '}';
    }
}
