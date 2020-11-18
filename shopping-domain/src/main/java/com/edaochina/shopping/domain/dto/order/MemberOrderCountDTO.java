package com.edaochina.shopping.domain.dto.order;

/**
 * @author jintian
 * @date 2019/4/1 15:55
 */
public class MemberOrderCountDTO {

    private String roleId;

    private String userId;

    private String name;

    private String phone;

    private Integer orderType;

    private String profitType;

    /**
     * 分润用户类型(1:商家,2:代理商，3：公司自己)
     */
    private Integer memberType;

    @Override
    public String toString() {
        return "MemberOrderCountDTO{" +
                "roleId='" + roleId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", orderType='" + orderType + '\'' +
                ", memberType=" + memberType +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer profitType) {
        this.orderType = profitType;
    }

    public String getProfitType() {
        return profitType;
    }

    public void setProfitType(String profitType) {
        this.profitType = profitType;
    }
}
