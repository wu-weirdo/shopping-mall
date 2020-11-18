package com.edaochina.shopping.domain.vo.sys;

import java.util.List;

public class SysMenuInfo {

    private String id;

    private String title;

    private String parentId;

    private List<SysMenuInfo> children;

    public SysMenuInfo(String id, String title, String parentId, List<SysMenuInfo> children) {
        this.id = id;
        this.title = title;
        this.parentId = parentId;
        this.children = children;
    }

    public SysMenuInfo(String id, List<SysMenuInfo> children) {
        this.id = id;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<SysMenuInfo> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuInfo> children) {
        this.children = children;
    }
}
