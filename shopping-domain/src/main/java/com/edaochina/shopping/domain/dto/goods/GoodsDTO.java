package com.edaochina.shopping.domain.dto.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
@Data
public class GoodsDTO implements Serializable {

    private String id;

    private String roleId;

    private String shopId;

    private String goodsName;

    private String goodsDetail;

    private String goodsTypeId;

    private String goodsSubclassId;

    private BigDecimal goodsPrice;

    private BigDecimal goodsRetailPrice;

    private BigDecimal goodsCostPrice;

    private Integer goodsSurplusNum;

    private String goodsSpec;

    private Integer approvalFlag;

    private String reason;

    private Integer limitBuyFlag;

    private Integer limitBuyNum;

    private Integer useCountDown;

    private BigDecimal useCountDownTime;

    private Integer orderNum;

    private Integer deleteFlag;
    /*    */
    /**
     * 商品详情 富文本
     *//*
    private String content;*/

    private String createId;

    private List<String> imgs;

    private List<GoodsTipsDTO> goodsTipsDTOs;

    private Integer stickStatus;

    private Integer stickWeight;

    private String goodsViewUrl;

    /**
     * 首页展示的图片地址
     */
    private String firstShowImg;

    // 3.1 新加内容

    /**
     * 活动有效期截止
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastValidTime;

    /**
     * 商品上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime putawayTime;

    /**
     * 拼团结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectLastValidTime;

    /**
     * 拼团信息(玩法)
     */
    private String collectInfo;

    /**
     * 是否参与拼团
     */
    private Integer collectFlag;

    /**
     * 拼团成团数量
     */
    private Integer collectNum;

    /**
     * 拼团价格
     */
    private BigDecimal collectPrice;

    /**
     * 核销时间段开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffStartTime;

    /**
     * 核销时间段结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffEndTime;

    private Boolean hot;

    private Integer promotion;

    private BigDecimal promotionCosts;

    @Override
    public String toString() {
        return "GoodsDTO{" +
                "id='" + id + '\'' +
                ", roleId='" + roleId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                ", goodsTypeId='" + goodsTypeId + '\'' +
                ", goodsSubclassId='" + goodsSubclassId + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsRetailPrice=" + goodsRetailPrice +
                ", goodsCostPrice=" + goodsCostPrice +
                ", goodsSurplusNum=" + goodsSurplusNum +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", approvalFlag=" + approvalFlag +
                ", reason='" + reason + '\'' +
                ", limitBuyFlag=" + limitBuyFlag +
                ", limitBuyNum=" + limitBuyNum +
                ", useCountDown=" + useCountDown +
                ", useCountDownTime=" + useCountDownTime +
                ", orderNum=" + orderNum +
                ", deleteFlag=" + deleteFlag +
                ", createId='" + createId + '\'' +
                ", imgs=" + imgs +
                ", goodsTipsDTOs=" + goodsTipsDTOs +
                ", stickStatus=" + stickStatus +
                ", stickWeight=" + stickWeight +
                ", goodsViewUrl='" + goodsViewUrl + '\'' +
                ", firstShowImg='" + firstShowImg + '\'' +
                ", lastValidTime=" + lastValidTime +
                ", putawayTime=" + putawayTime +
                ", collectLastValidTime=" + collectLastValidTime +
                ", collectInfo='" + collectInfo + '\'' +
                ", collectFlag=" + collectFlag +
                ", collectNum=" + collectNum +
                ", collectPrice=" + collectPrice +
                ", writeOffStartTime=" + writeOffStartTime +
                ", writeOffEndTime=" + writeOffEndTime +
                ", hot=" + hot +
                ", promotion=" + promotion +
                ", promotionCosts=" + promotionCosts +
                '}';
    }

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsSubclassId() {
        return goodsSubclassId;
    }

    public void setGoodsSubclassId(String goodsSubclassId) {
        this.goodsSubclassId = goodsSubclassId;
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

    public Integer getGoodsSurplusNum() {
        return goodsSurplusNum;
    }

    public void setGoodsSurplusNum(Integer goodsSurplusNum) {
        this.goodsSurplusNum = goodsSurplusNum;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Integer getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(Integer approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<GoodsTipsDTO> getGoodsTipsDTOs() {
        return goodsTipsDTOs;
    }

    public void setGoodsTipsDTOs(List<GoodsTipsDTO> goodsTipsDTOs) {
        this.goodsTipsDTOs = goodsTipsDTOs;
    }

   /* public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }*/

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Integer getStickStatus() {
        return stickStatus;
    }

    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    public Integer getStickWeight() {
        return stickWeight;
    }

    public void setStickWeight(Integer stickWeight) {
        this.stickWeight = stickWeight;
    }

    public String getGoodsViewUrl() {
        return goodsViewUrl;
    }

    public void setGoodsViewUrl(String goodsViewUrl) {
        this.goodsViewUrl = goodsViewUrl;
    }

    public String getFirstShowImg() {
        return firstShowImg;
    }

    public void setFirstShowImg(String firstShowImg) {
        this.firstShowImg = firstShowImg;
    }

    public String getCollectInfo() {
        return collectInfo;
    }

    public void setCollectInfo(String collectInfo) {
        this.collectInfo = collectInfo;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public LocalDateTime getLastValidTime() {
        return lastValidTime;
    }

    public void setLastValidTime(LocalDateTime lastValidTime) {
        this.lastValidTime = lastValidTime;
    }

    public LocalDateTime getPutawayTime() {
        return putawayTime;
    }

    public void setPutawayTime(LocalDateTime putawayTime) {
        this.putawayTime = putawayTime;
    }

    public LocalDateTime getCollectLastValidTime() {
        return collectLastValidTime;
    }

    public void setCollectLastValidTime(LocalDateTime collectLastValidTime) {
        this.collectLastValidTime = collectLastValidTime;
    }

    public BigDecimal getCollectPrice() {
        return collectPrice;
    }

    public void setCollectPrice(BigDecimal collectPrice) {
        this.collectPrice = collectPrice;
    }

    public LocalDateTime getWriteOffStartTime() {
        return writeOffStartTime;
    }

    public void setWriteOffStartTime(LocalDateTime writeOffStartTime) {
        this.writeOffStartTime = writeOffStartTime;
    }

    public LocalDateTime getWriteOffEndTime() {
        return writeOffEndTime;
    }

    public void setWriteOffEndTime(LocalDateTime writeOffEndTime) {
        this.writeOffEndTime = writeOffEndTime;
    }

}
