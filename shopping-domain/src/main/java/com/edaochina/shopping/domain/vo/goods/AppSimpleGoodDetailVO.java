package com.edaochina.shopping.domain.vo.goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class AppSimpleGoodDetailVO implements Serializable {

    private String id;

    private String shopId;

    private String shopName;

    private String shopAddress;

    private String shopPhone;

    private String goodsName;

    private String goodsDetail;

    private BigDecimal goodsPrice;

    private BigDecimal goodsRetailPrice;

    private BigDecimal goodsCostPrice;

    private Integer limitBuyFlag;

    private Integer limitBuyNum;

    private String deliveryFee;

    private String imgs;

    private double distance;

    private double longitude = 0.00;

    private double latitude = 0.00;

    private List<SysGoodsTipsVO> sysGoodsTipsVOS;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
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

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsRetailPrice() {
        return goodsRetailPrice;
    }

    public void setGoodsRetailPrice(BigDecimal goodsRetailPrice) {
        this.goodsRetailPrice = goodsRetailPrice;
    }

    public BigDecimal getGoodsCostPrice() {
        return goodsCostPrice;
    }

    public void setGoodsCostPrice(BigDecimal goodsCostPrice) {
        this.goodsCostPrice = goodsCostPrice;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<SysGoodsTipsVO> getSysGoodsTipsVOS() {
        return sysGoodsTipsVOS;
    }

    public void setSysGoodsTipsVOS(List<SysGoodsTipsVO> sysGoodsTipsVOS) {
        this.sysGoodsTipsVOS = sysGoodsTipsVOS;
    }

    public Integer getLimitBuyFlag() {
        return limitBuyFlag;
    }

    public void setLimitBuyFlag(Integer limitBuyFlag) {
        this.limitBuyFlag = limitBuyFlag;
    }

    public Integer getLimitBuyNum() {
        return limitBuyNum;
    }

    public void setLimitBuyNum(Integer limitBuyNum) {
        this.limitBuyNum = limitBuyNum;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
