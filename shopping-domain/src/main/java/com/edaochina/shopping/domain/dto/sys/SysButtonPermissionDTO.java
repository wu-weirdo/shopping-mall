package com.edaochina.shopping.domain.dto.sys;

import java.util.List;

public class SysButtonPermissionDTO {

    private Integer roleId;

    private List<Integer> buttonIds;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getButtonIds() {
        return buttonIds;
    }

    public void setButtonIds(List<Integer> buttonIds) {
        this.buttonIds = buttonIds;
    }
}
