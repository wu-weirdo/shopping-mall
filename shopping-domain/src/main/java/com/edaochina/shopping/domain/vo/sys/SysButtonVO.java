package com.edaochina.shopping.domain.vo.sys;

public class SysButtonVO {

    private Integer id;

    /**
     * 按钮别名
     */
    private String buttonAlias;

    /**
     * 按钮功能
     */
    private String buttonFunction;

    /**
     * 所属菜单
     */
    private String menuId;

    /**
     * 父菜单
     */
    private String parentMenuId;

    private boolean isChecked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getButtonAlias() {
        return buttonAlias;
    }

    public void setButtonAlias(String buttonAlias) {
        this.buttonAlias = buttonAlias;
    }

    public String getButtonFunction() {
        return buttonFunction;
    }

    public void setButtonFunction(String buttonFunction) {
        this.buttonFunction = buttonFunction;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
