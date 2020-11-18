package com.edaochina.shopping.domain.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AppOrderDetailVO implements Serializable {
    /**
     * 订单id
     */
    private String id;

    /**
     * 商家id
     */
    private String shoperId;

    /**
     * 商家名称
     */
    private String shoperName;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 规格
     */
    private String goodsSpec;
    /**
     * 图片
     */
    private String goodsImg;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户地址
     */
    private String userAddress;

    private String useCode;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 购买数量
     */
    private Integer goodsNum;

    private BigDecimal totalPrice;

    private BigDecimal actualPrice;

    private BigDecimal costPrice;
    /**
     * 状态
     */
    private Integer status;

    private String remark;

    /**
     * 核销时间（提货时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffTime;
    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 有无异常订单信息
     */
    private Boolean hasErrorFlag;

    /**
     * 是否有退款(0:无退款，1:有退款,退款中，2:有退款已拒绝，3：有退款已退款)
     */
    private int hasRefundFlag = -1;

    /**
     * 商家手机号
     */
    private String shopPhone;

    /**
     * 最后有效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useLastValidTime;

    private Integer collectStatus;

    /**
     * 订单使用有效期
     */
    private Integer goodsUseCountDown;

    public int getHasRefundFlag() {
        return hasRefundFlag;
    }

    public void setHasRefundFlag(int hasRefundFlag) {
        this.hasRefundFlag = hasRefundFlag;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getShoperId() {
        return shoperId;
    }

    public void setShoperId(String shoperId) {
        this.shoperId = shoperId;
    }

    public String getShoperName() {
        return shoperName;
    }

    public void setShoperName(String shoperName) {
        this.shoperName = shoperName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }


    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUseCode() {
        return useCode;
    }

    public void setUseCode(String useCode) {
        this.useCode = useCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getWriteOffTime() {
        return writeOffTime;
    }

    public void setWriteOffTime(LocalDateTime writeOffTime) {
        this.writeOffTime = writeOffTime;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Boolean getHasErrorFlag() {
        return hasErrorFlag;
    }

    public void setHasErrorFlag(Boolean hasErrorFlag) {
        this.hasErrorFlag = hasErrorFlag;
    }

    public LocalDateTime getUseLastValidTime() {
        return useLastValidTime;
    }

    public void setUseLastValidTime(LocalDateTime useLastValidTime) {
        this.useLastValidTime = useLastValidTime;
    }

    public Integer getGoodsUseCountDown() {
        return goodsUseCountDown;
    }

    public void setGoodsUseCountDown(Integer goodsUseCountDown) {
        this.goodsUseCountDown = goodsUseCountDown;
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }
}
