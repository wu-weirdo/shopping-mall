package com.edaochina.shopping.domain.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SysOrderDetailVO implements Serializable {

    private String id;

    private String orderNo;

    private String goodsTypeName;

    private String shoperId;

    private String shoperName;

    private String shoperAddress;

    private String shoperArea;

    private String goodsId;

    private String goodsName;

    private String goodsDetail;

    private String userId;

    private String userName;

    private String phone;

    private String userAddress;

    private String useCode;

    private Integer goodsNum;

    private BigDecimal totalPrice;

    private Integer orderType;

    private Integer useCountDown;

    private BigDecimal useCountDownTime;

    private Integer status;

    private Integer teamMemberNum;

    private Integer limitBuyFlag;

    private Integer limitBuyNum;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 最后有效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useLastValidTime;

    /**
     * 核销时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffTime;

    /**
     * 核销状态
     */
    private Integer writeOffStatus;

    public LocalDateTime getUseLastValidTime() {
        return useLastValidTime;
    }

    public void setUseLastValidTime(LocalDateTime useLastValidTime) {
        this.useLastValidTime = useLastValidTime;
    }

    public Integer getWriteOffStatus() {
        return writeOffStatus;
    }

    public void setWriteOffStatus(Integer writeOffStatus) {
        this.writeOffStatus = writeOffStatus;
    }

    public LocalDateTime getWriteOffTime() {
        return writeOffTime;
    }

    public void setWriteOffTime(LocalDateTime writeOffTime) {
        this.writeOffTime = writeOffTime;
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

    public String getShoperAddress() {
        return shoperAddress;
    }

    public void setShoperAddress(String shoperAddress) {
        this.shoperAddress = shoperAddress;
    }

    public String getShoperArea() {
        return shoperArea;
    }

    public void setShoperArea(String shoperArea) {
        this.shoperArea = shoperArea;
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

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getUseCountDown() {
        return useCountDown;
    }

    public void setUseCountDown(Integer useCountDown) {
        this.useCountDown = useCountDown;
    }

    public BigDecimal getUseCountDownTime() {
        return useCountDownTime;
    }

    public void setUseCountDownTime(BigDecimal useCountDownTime) {
        this.useCountDownTime = useCountDownTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTeamMemberNum() {
        return teamMemberNum;
    }

    public void setTeamMemberNum(Integer teamMemberNum) {
        this.teamMemberNum = teamMemberNum;
    }

    public Integer getLimitBuyFlag() {
        return limitBuyFlag;
    }

    public void setLimitBuyFlag(Integer limitBuyFlag) {
        this.limitBuyFlag = limitBuyFlag;
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

    public Integer getLimitBuyNum() {
        return limitBuyNum;
    }

    public void setLimitBuyNum(Integer limitBuyNum) {
        this.limitBuyNum = limitBuyNum;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }
}
