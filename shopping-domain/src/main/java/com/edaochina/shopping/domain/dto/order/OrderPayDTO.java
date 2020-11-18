package com.edaochina.shopping.domain.dto.order;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class OrderPayDTO implements Serializable {

    private String orderId;

    private String merchantId;

    private String openId;

    private String userId;
    // 邀请码
    private String invitatCode;

    @ApiModelProperty("是否是app支付，0：小程序，1:app,默认为小程序")
    private Integer isAppPay;

    public Integer getIsAppPay() {
        return isAppPay;
    }

    public void setIsAppPay(Integer isAppPay) {
        this.isAppPay = isAppPay;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInvitatCode() {
        return invitatCode;
    }

    public void setInvitatCode(String invitatCode) {
        this.invitatCode = invitatCode;
    }
}
