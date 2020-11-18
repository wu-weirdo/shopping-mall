package com.edaochina.shopping.domain.entity.trade;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 退款记录表
 * </p>
 *
 * @since 2019-06-11
 */
public class PayRefundInfo {


    private Integer id;

    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 商户退款单号
     */
    private String outRefundNo;

    /**
     * 订单金额
     */
    private BigDecimal totalFee;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;

    /**
     * 退款结果(0退款失败，1退款成功)
     */
    private Integer refundResult;

    /**
     * 返回结果
     */
    private String returnResult;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

    // 2019.10.08 添加字段
    /**
     * 退款申请id
     */
    private Integer refundApplyId;

    /**
     * 退款申请回调内容
     */
    private String callBackResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(BigDecimal refundFee) {
        this.refundFee = refundFee;
    }

    public Integer getRefundResult() {
        return refundResult;
    }

    public void setRefundResult(Integer refundResult) {
        this.refundResult = refundResult;
    }

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRefundApplyId() {
        return refundApplyId;
    }

    public void setRefundApplyId(Integer refundApplyId) {
        this.refundApplyId = refundApplyId;
    }

    public String getCallBackResult() {
        return callBackResult;
    }

    public void setCallBackResult(String callBackResult) {
        this.callBackResult = callBackResult;
    }
}
