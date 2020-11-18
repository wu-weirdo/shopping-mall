package com.edaochina.shopping.domain.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * UserMemberPayDTO
 *
 * @author wangpenglei
 * @since 2019/9/23 15:55
 */
@ApiModel("用户会员费微信起调支付请求")
public class UserMemberPayDTO {

    /**
     * 支付用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;
    /**
     * 支付订单类型
     */
    @ApiModelProperty(value = "支付订单类型")
    private Integer orderType;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalPrice;
    /**
     * 用户标识
     */
    @ApiModelProperty(value = "用户标识", required = true)
    private String openId;

    /**
     * 区县id
     */
    @ApiModelProperty(value = "区县id", required = false)
    private String countyId;

    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区id", required = false)
    private String communiyId;

    /**
     * 店铺id
     */
    @ApiModelProperty("店铺id")
    private String shopId;

    @Override
    public String toString() {
        return "UserMemberPayDTO{" +
                "userId='" + userId + '\'' +
                ", orderType=" + orderType +
                ", totalPrice=" + totalPrice +
                ", openId='" + openId + '\'' +
                ", countyId='" + countyId + '\'' +
                ", communiyId='" + communiyId + '\'' +
                ", shopId='" + shopId + '\'' +
                '}';
    }

    public String getCommuniyId() {
        return communiyId;
    }

    public void setCommuniyId(String communiyId) {
        this.communiyId = communiyId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
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

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
