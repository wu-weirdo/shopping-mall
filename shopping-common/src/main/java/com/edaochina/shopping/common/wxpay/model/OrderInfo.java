package com.edaochina.shopping.common.wxpay.model;

import java.math.BigDecimal;

/**
 * 微信预订单
 *
 * @author zuoliangzhu
 */
public class OrderInfo {
    private String appId;// 小程序ID
    private String mchId;// 商户号
    private String userId; // 支付用户id
    private Integer orderType;// 支付订单类型
    private String nonceStr;// 随机字符串
    private String signType;//签名类型
    private String sign;// 签名
    private String body;// 商品描述
    private String attach;// 数据包装
    private String outTradeNo;// 商户订单号
    private long totalFee;// 标价金额 ,单位为分
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    private String feeType;//标价类型
    private String spbillCreateIp;// 终端IP
    private String notifyUrl;// 通知地址
    private String tradeType;// 交易类型
    private String openId;//用户标识
    private String limitPay;//指定用什么支付
    private String deviceInfo;//终端设备号

    @Override
    public String toString() {
        return "OrderInfo{" +
                "appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderType=" + orderType +
                ", nonceStr='" + nonceStr + '\'' +
                ", signType='" + signType + '\'' +
                ", sign='" + sign + '\'' +
                ", body='" + body + '\'' +
                ", attach='" + attach + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", totalFee=" + totalFee +
                ", totalPrice=" + totalPrice +
                ", feeType='" + feeType + '\'' +
                ", spbillCreateIp='" + spbillCreateIp + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", openId='" + openId + '\'' +
                ", limitPay='" + limitPay + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                '}';
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
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
}
