package com.edaochina.shopping.domain.dto.goods;

import com.edaochina.shopping.domain.base.page.Pages;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author 24h
 */
@ApiModel("小程序查询商品请求")
public class QueryAppGoodsDTO implements Serializable {
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("商品id")
    private String goodsId;
    @ApiModelProperty("小区id")
    private String community;
    @ApiModelProperty("商家名称")
    private String title;
    /**
     * 搜索值
     */
    @ApiModelProperty("商品名称")
    private String goodsName;
    @ApiModelProperty("商品类型id")
    private String goodsTypeId;
    @ApiModelProperty("商品子类类型id")
    private String goodsSubclassId;
    @ApiModelProperty("用户经度")
    private Double longitude = 0.00;
    @ApiModelProperty("用户维度")
    private Double latitude = 0.00;
    @ApiModelProperty("展示最大距离，小于等于0未全查询")
    private Long maxDistance;
    private Boolean hot;
    private Boolean goodsSalesOrder;
    private Pages pages = new Pages();

    @Override
    public String toString() {
        return "QueryAppGoodsDTO{" +
                "userId='" + userId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", community='" + community + '\'' +
                ", title='" + title + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsTypeId='" + goodsTypeId + '\'' +
                ", goodsSubclassId='" + goodsSubclassId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", maxDistance=" + maxDistance +
                ", hot=" + hot +
                ", goodsSalesOrder=" + goodsSalesOrder +
                ", pages=" + pages +
                '}';
    }

    public Boolean getGoodsSalesOrder() {
        return goodsSalesOrder;
    }

    public void setGoodsSalesOrder(Boolean goodsSalesOrder) {
        this.goodsSalesOrder = goodsSalesOrder;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public Long getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Long maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getGoodsSubclassId() {
        return goodsSubclassId;
    }

    public void setGoodsSubclassId(String goodsSubclassId) {
        this.goodsSubclassId = goodsSubclassId;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }
}
