package com.edaochina.shopping.domain.vo.goods;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SysGoodsVO implements Serializable {

    private String id;

    private String shopName;

    private String goodsName;

    private String imgUrl;

    private BigDecimal goodsPrice;

    private Integer approvalFlag;

    /**
     * 商品剩余数量
     */
    private Integer goodsSurplusNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 置顶状态
     */
    private Integer stickStatus;


    /**
     * 上架状态
     */
    private int putawayStatus;


    private int putawayFlag;

    private int notPutawayFlag;

    /**
     * 是否参与拼团(0:不参与，1：参与)
     */
    private Integer collectFlag;

    private Integer goodsSales;

    private Boolean hot;

    /**
     * 是否推广(默认否)
     */
    private Integer promotion;

    /**
     * 推广费率(默认0.05)
     */
    private BigDecimal promotionCosts;

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public BigDecimal getPromotionCosts() {
        return promotionCosts;
    }

    public void setPromotionCosts(BigDecimal promotionCosts) {
        this.promotionCosts = promotionCosts;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(Integer approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

    public Integer getGoodsSurplusNum() {
        return goodsSurplusNum;
    }

    public void setGoodsSurplusNum(Integer goodsSurplusNum) {
        this.goodsSurplusNum = goodsSurplusNum;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getStickStatus() {
        return stickStatus;
    }

    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    public int getPutawayStatus() {
        return putawayStatus;
    }

    public void setPutawayStatus(int putawayStatus) {
        this.putawayStatus = putawayStatus;
    }

    public int getPutawayFlag() {
        return putawayFlag;
    }

    public void setPutawayFlag(int putawayFlag) {
        this.putawayFlag = putawayFlag;
    }

    public int getNotPutawayFlag() {
        return notPutawayFlag;
    }

    public void setNotPutawayFlag(int notPutawayFlag) {
        this.notPutawayFlag = notPutawayFlag;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }

    public Integer getGoodsSales() {
        return goodsSales;
    }

    public void setGoodsSales(Integer goodsSales) {
        this.goodsSales = goodsSales;
    }
}
