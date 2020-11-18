package com.edaochina.shopping.domain.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jintian
 * @date 2019/6/27 17:15
 */
public class AppCollectOrderDetailVO {

    private String shoperId;

    private String shoperName;

    private String goodsName;

    private BigDecimal price;


    private BigDecimal goodsRetailPrice;


    private String collectNum;

    private String buyedNum;

    private String num;

    private String img;

    private String orderId;

    private String createTime;

    private String userId;

    private String userPhone;

    private Integer collectStatus;


    private String userName;

    /**
     * 拼团有效期截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectLastValidTime;


    private String userAvatar;

    private Integer orderStatus;

    private String goodsId;

    private Integer sortNum;

    private Integer hasRefundFlag;

    /**
     * 有无异常订单信息
     */
    private Boolean hasErrorFlag;

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(String collectNum) {
        this.collectNum = collectNum;
    }

    public String getBuyedNum() {
        return buyedNum;
    }

    public void setBuyedNum(String buyedNum) {
        this.buyedNum = buyedNum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCollectLastValidTime() {
        return collectLastValidTime;
    }

    public void setCollectLastValidTime(LocalDateTime collectLastValidTime) {
        this.collectLastValidTime = collectLastValidTime;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getHasRefundFlag() {
        return hasRefundFlag;
    }

    public void setHasRefundFlag(Integer hasRefundFlag) {
        this.hasRefundFlag = hasRefundFlag;
    }

    public Boolean getHasErrorFlag() {
        return hasErrorFlag;
    }

    public void setHasErrorFlag(Boolean hasErrorFlag) {
        this.hasErrorFlag = hasErrorFlag;
    }

    public BigDecimal getGoodsRetailPrice() {
        return goodsRetailPrice;
    }

    public void setGoodsRetailPrice(BigDecimal goodsRetailPrice) {
        this.goodsRetailPrice = goodsRetailPrice;
    }
}
