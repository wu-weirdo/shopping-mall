package com.edaochina.shopping.domain.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class AppRefundApplyVO {

    private Integer id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 退款进度(10退款申请处理中，20已拒绝，30已同意待退款，40已同意已退款，50已取消)
     */
    private Integer refundStatus;

    /**
     * 申请退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyRefundTime;

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
     * 处理退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime handleRefundTime;

    /**
     * 备注
     */
    private String shoperRefuseReason;

    /**
     * 退款申请图片凭证
     */
    private List<String> refundImgs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public LocalDateTime getApplyRefundTime() {
        return applyRefundTime;
    }


    public void setApplyRefundTime(LocalDateTime applyRefundTime) {
        this.applyRefundTime = applyRefundTime;
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

    public LocalDateTime getHandleRefundTime() {
        return handleRefundTime;
    }

    public void setHandleRefundTime(LocalDateTime handleRefundTime) {
        this.handleRefundTime = handleRefundTime;
    }

    public String getShoperRefuseReason() {
        return shoperRefuseReason;
    }

    public void setShoperRefuseReason(String shoperRefuseReason) {
        this.shoperRefuseReason = shoperRefuseReason;
    }

    public List<String> getRefundImgs() {
        return refundImgs;
    }

    public void setRefundImgs(List<String> refundImgs) {
        this.refundImgs = refundImgs;
    }
}
