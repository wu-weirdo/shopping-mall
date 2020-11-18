package com.edaochina.shopping.domain.vo.order;

import java.math.BigDecimal;

/**
 * 小程序用户提货单
 *
 * @author 24h
 */
public class AppTakeGoodsVO {

    private String id;

    /**
     * 商品规格
     */
    private String goodsSpec;

    /**
     * 商品图片
     */
    private String imgUrl;

    private String shoperName;

    private String goodsName;

    private Integer goodsNum;

    private BigDecimal actualPrice;

    private BigDecimal totalPrice;

    private BigDecimal costPrice;

    private String status;

    private Integer writeOffStatus;

    private String shoperId;

    private String goodsId;

    private Integer collectFlag;

    @Override
    public String toString() {
        return "AppTakeGoodsVO{" +
                "id='" + id + '\'' +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", shoperName='" + shoperName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsNum=" + goodsNum +
                ", actualPrice=" + actualPrice +
                ", totalPrice=" + totalPrice +
                ", costPrice=" + costPrice +
                ", status='" + status + '\'' +
                ", writeOffStatus=" + writeOffStatus +
                ", shoperId='" + shoperId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", collectFlag=" + collectFlag +
                '}';
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getShoperId() {
        return shoperId;
    }

    public void setShoperId(String shoperId) {
        this.shoperId = shoperId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }
}
