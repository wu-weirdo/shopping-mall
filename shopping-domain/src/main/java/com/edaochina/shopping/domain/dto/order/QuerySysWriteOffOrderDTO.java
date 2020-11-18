package com.edaochina.shopping.domain.dto.order;

import com.edaochina.shopping.domain.base.page.Pages;

import java.io.Serializable;
import java.util.Date;

public class QuerySysWriteOffOrderDTO implements Serializable {
    private String goodsName;
    private String goodsId;
    private String id;
    private String phone;

    private String userId;

    private String shopId;

    private String agentId;

    private Date writeOffStartTime;

    private Date writeOffEndTime;

    private Pages pages;

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


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }
}
