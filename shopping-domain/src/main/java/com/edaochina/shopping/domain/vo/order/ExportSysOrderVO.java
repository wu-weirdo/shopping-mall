package com.edaochina.shopping.domain.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ExportSysOrderVO implements Serializable {

    private String id;

    private String orderNo;

    private String shoperName;

    private String goodsName;

    private String goodsTypeName;

    private String userId;

    private String userName;

    private String nickname;

    private String phone;

    private String address;

    private Integer goodsNum;

    private BigDecimal totalPrice;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExportSysOrderVO)) {
            return false;
        }
        ExportSysOrderVO that = (ExportSysOrderVO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getOrderNo(), that.getOrderNo()) &&
                Objects.equals(getShoperName(), that.getShoperName()) &&
                Objects.equals(getGoodsName(), that.getGoodsName()) &&
                Objects.equals(getGoodsTypeName(), that.getGoodsTypeName()) &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getUserName(), that.getUserName()) &&
                Objects.equals(getNickname(), that.getNickname()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getGoodsNum(), that.getGoodsNum()) &&
                Objects.equals(getTotalPrice(), that.getTotalPrice()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getCreateTime(), that.getCreateTime()) &&
                Objects.equals(getWriteOffTime(), that.getWriteOffTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderNo(), getShoperName(), getGoodsName(), getGoodsTypeName(), getUserId(), getUserName(), getNickname(), getPhone(), getAddress(), getGoodsNum(), getTotalPrice(), getStatus(), getCreateTime(), getWriteOffTime());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShoperName() {
        return shoperName;
    }

    public void setShoperName(String shoperName) {
        this.shoperName = shoperName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getWriteOffTime() {
        return writeOffTime;
    }

    public void setWriteOffTime(LocalDateTime writeOffTime) {
        this.writeOffTime = writeOffTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
