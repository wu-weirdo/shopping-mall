package com.edaochina.shopping.domain.dto.sys;

import java.util.List;

/**
 * SysUserPermissionDTO
 *
 * @author wangpenglei
 * @since 2019/8/2 14:03
 */
public class SysUserPermissionDTO {

    private List<String> menuIds;

    private String userId;

    private String memberType;

    @Override
    public String toString() {
        return "SysUserButtonPermissionDTO{" +
                "menuIds=" + menuIds +
                ", userId='" + userId + '\'' +
                ", memberType='" + memberType + '\'' +
                '}';
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
}
