package com.edaochina.shopping.domain.dto.trade;

import java.util.List;

public class RefundApplyDTO {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 退款方式
     */
    private String refundMethod;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 申请退款金额
     */
    private String applyFee;

    /**
     * 退款说明
     */
    private String refundExplain;

    /**
     * 退款申请图片凭证
     */
    private List<String> imgs;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(String refundMethod) {
        this.refundMethod = refundMethod;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getApplyFee() {
        return applyFee;
    }

    public void setApplyFee(String applyFee) {
        this.applyFee = applyFee;
    }

    public String getRefundExplain() {
        return refundExplain;
    }

    public void setRefundExplain(String refundExplain) {
        this.refundExplain = refundExplain;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
