package com.edaochina.shopping.domain.dto.sys;

import java.util.List;

public class SysPermissionDTO {

    private Integer roleId;

    private List<String> menuIds;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }
}
