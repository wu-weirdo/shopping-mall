package com.edaochina.shopping.domain.dto.goods;

import com.edaochina.shopping.domain.base.page.Pages;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 24h
 */
public class QueryGoodsDTO implements Serializable {

    private String shopId;

    private String agentId;

    private String goodsName;

    private String shopName;

    private String goodsId;

    private String goodsTypeId;

    /**
     * 商品子类
     */
    private String goodsSubclassId;

    private Integer approvalFlag;

    private Date startTime;

    private Date endTime;

    private String communty;

    private Integer stickStatus;

    private Integer countyId;

    private Integer collectFlag;

    /**
     * 判断查询的是上架时间或者下架时间
     */
    private String timeField;

    private Integer putawayStatus;

    private Boolean hot;

    /**
     * 上架状态
     */
    private Integer putawayFlag;

    private Integer supplyChain;

    @Override
    public String toString() {
        return "QueryGoodsDTO{" +
                "shopId='" + shopId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsTypeId='" + goodsTypeId + '\'' +
                ", goodsSubclassId='" + goodsSubclassId + '\'' +
                ", approvalFlag=" + approvalFlag +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", communty='" + communty + '\'' +
                ", stickStatus=" + stickStatus +
                ", countyId=" + countyId +
                ", collectFlag=" + collectFlag +
                ", timeField='" + timeField + '\'' +
                ", putawayStatus=" + putawayStatus +
                ", hot=" + hot +
                ", putawayFlag=" + putawayFlag +
                ", supplyChain=" + supplyChain +
                ", pages=" + pages +
                '}';
    }

    public Integer getSupplyChain() {
        return supplyChain;
    }

    public void setSupplyChain(Integer supplyChain) {
        this.supplyChain = supplyChain;
    }

    private Pages pages;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }

    public Integer getPutawayStatus() {
        return putawayStatus;
    }

    public void setPutawayStatus(Integer putawayStatus) {
        this.putawayStatus = putawayStatus;
    }

    public String getTimeField() {
        return timeField;
    }

    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Integer getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(Integer approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public String getCommunty() {
        return communty;
    }

    public void setCommunty(String communty) {
        this.communty = communty;
    }

    public Integer getStickStatus() {
        return stickStatus;
    }

    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    public Integer getPutawayFlag() {
        return putawayFlag;
    }

    public void setPutawayFlag(Integer putawayFlag) {
        this.putawayFlag = putawayFlag;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public String getGoodsSubclassId() {
        return goodsSubclassId;
    }

    public void setGoodsSubclassId(String goodsSubclassId) {
        this.goodsSubclassId = goodsSubclassId;
    }
}
