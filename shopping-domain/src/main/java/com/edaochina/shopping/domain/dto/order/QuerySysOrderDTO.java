package com.edaochina.shopping.domain.dto.order;

import com.edaochina.shopping.domain.base.page.Pages;

import java.io.Serializable;
import java.util.Date;

public class QuerySysOrderDTO implements Serializable {

    private String id;

    private String userId;

    private String shopId;

    private String agentId;

    private String shoperName;

    private String goodsName;

    private Date collectStartTime;

    private Date collectEndTime;

    private Integer orderType;

    private Integer status;

    private Integer deliverStatus;

    private String phone;

    private String userCode;

    private String goodsId;

    private Date writeOffStartTime;

    private Date writeOffEndTime;

    private Pages pages;

    private String userNickName;

    /**
     * 核销状态(0:未核销，1已核销)
     */
    private Integer writeOffStatus;

    public Integer getWriteOffStatus() {
        return writeOffStatus;
    }

    public void setWriteOffStatus(Integer writeOffStatus) {
        this.writeOffStatus = writeOffStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
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

    public Date getCollectStartTime() {
        return collectStartTime;
    }

    public void setCollectStartTime(Date collectStartTime) {
        this.collectStartTime = collectStartTime;
    }

    public Date getCollectEndTime() {
        return collectEndTime;
    }

    public void setCollectEndTime(Date collectEndTime) {
        this.collectEndTime = collectEndTime;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Date getWriteOffStartTime() {
        return writeOffStartTime;
    }

    public void setWriteOffStartTime(Date writeOffStartTime) {
        this.writeOffStartTime = writeOffStartTime;
    }

    public Date getWriteOffEndTime() {
        return writeOffEndTime;
    }

    public void setWriteOffEndTime(Date writeOffEndTime) {
        this.writeOffEndTime = writeOffEndTime;
    }

    public Integer getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(Integer deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
}
