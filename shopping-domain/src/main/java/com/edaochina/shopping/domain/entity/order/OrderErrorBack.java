package com.edaochina.shopping.domain.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 异常订单反馈
 * </p>
 *
 * @author ${author}
 * @since 2019-04-28
 */
public class OrderErrorBack {

    private Integer id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态（10待分享，20待使用，30待评价，40已退款，50已评价，60已过期，70待支付，80付款失败,90异常状态
     */
    private Integer orderStatus;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

    /**
     * 核销时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 商家名称
     */
    private String merchantTitle;

    /**
     * 商家手机号码
     */
    private String merchantPhone;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 异常原因
     */
    private String exceptionReason;

    /**
     * 来源(1用户，2商家)
     */
    private Integer origin;

    /**
     * 核销状态(0:未核销，1已核销，2未确定)
     */
    private Integer writeOffStatus;

    /**
     * 处理结果(1未处理，2已处理)
     */
    private Integer handleResult;

    /**
     * 备注
     */
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime handleTime;

    public LocalDateTime getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(LocalDateTime handleTime) {
        this.handleTime = handleTime;
    }

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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getWriteOffTime() {
        return writeOffTime;
    }

    public void setWriteOffTime(LocalDateTime writeOffTime) {
        this.writeOffTime = writeOffTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantTitle() {
        return merchantTitle;
    }

    public void setMerchantTitle(String merchantTitle) {
        this.merchantTitle = merchantTitle;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getExceptionReason() {
        return exceptionReason;
    }

    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getWriteOffStatus() {
        return writeOffStatus;
    }

    public void setWriteOffStatus(Integer writeOffStatus) {
        this.writeOffStatus = writeOffStatus;
    }

    public Integer getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(Integer handleResult) {
        this.handleResult = handleResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
