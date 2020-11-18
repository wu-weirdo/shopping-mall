package com.edaochina.shopping.domain.vo.sys;

import java.util.List;

public class SysPermissionInfo {

    private String id;

    private String title;

    private List<SysButtonVO> children;

    public SysPermissionInfo(String id, String title, List<SysButtonVO> children) {
        this.id = id;
        this.title = title;
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

    public List<SysButtonVO> getChildren() {
        return children;
    }

    public void setChildren(List<SysButtonVO> children) {
        this.children = children;
    }
}
