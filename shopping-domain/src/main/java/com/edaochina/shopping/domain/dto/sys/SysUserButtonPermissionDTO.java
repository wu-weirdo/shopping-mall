package com.edaochina.shopping.domain.dto.sys;

import java.util.List;

/**
 * SysUserButtonPermissionDTO
 *
 * @author wangpenglei
 * @since 2019/8/2 14:03
 */
public class SysUserButtonPermissionDTO {

    private List<Integer> buttonIds;

    private String userId;

    private String memberType;

    @Override
    public String toString() {
        return "SysUserButtonPermissionDTO{" +
                "buttonIds=" + buttonIds +
                ", userId='" + userId + '\'' +
                ", memberType='" + memberType + '\'' +
                '}';
    }

    public List<Integer> getButtonIds() {
        return buttonIds;
    }

    public void setButtonIds(List<Integer> buttonIds) {
        this.buttonIds = buttonIds;
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
