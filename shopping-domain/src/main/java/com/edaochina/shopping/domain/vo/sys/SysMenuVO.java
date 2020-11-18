package com.edaochina.shopping.domain.vo.sys;

public class SysMenuVO {

    /**
     * 菜单编号
     */
    private String id;

    /**
     * 菜单父编号 -1为无父编号
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
