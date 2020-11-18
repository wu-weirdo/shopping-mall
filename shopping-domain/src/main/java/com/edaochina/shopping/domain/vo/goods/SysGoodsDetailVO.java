package com.edaochina.shopping.domain.vo.goods;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SysGoodsDetailVO implements Serializable {

    private String id;

    private String shopId;

    private String shopName;

    private String goodsName;

    private String goodsDetail;

    private String goodsTypeId;

    private String goodsTypeName;

    private String goodsSubclassId;

    private String goodsSubclassName;

    private BigDecimal goodsPrice;

    private BigDecimal goodsRetailPrice;

    private BigDecimal goodsCostPrice;

    private Integer goodsNum;

    private Integer goodsSurplusNum;

    private String goodsSpec;

    private Integer approvalFlag;

    private Integer limitBuyFlag;

    private Integer limitBuyNum;

    private Integer activityCountDown;

    private BigDecimal activityCountDownTime;

    private Integer useCountDown;

    private BigDecimal useCountDownTime;

    private Integer orderNum;

    private String content;

    /**
     * 置顶状态
     */
    private Integer stickStatus;

    /**
     * 置顶权重
     */
    private Integer stickWeight;

    /**
     * 商品的小视频地址
     */
    private String goodsViewUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private List<String> goodsImgs;

    private List<SysGoodsTipsVO> sysGoodsTipsVOs;

    /**
     * 首页展示的图片地址
     */
    private String firstShowImg;

    // 3.2 新添加的
    /**
     * 拼团标识
     */
    private Integer collectFlag;
    /**
     * 拼团成团数量
     */
    private Integer collectNum;

    /**
     * 拼团最后有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectLastValidTime;

    /**
     * 拼团价格
     */
    private BigDecimal collectPrice;

    /**
     * 拼团有效期（拼团玩法）
     */
    private String collectInfo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastValidTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime putawayTime;

    /**
     * 销量
     */
    private Integer goodsSales;

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

    /**
     * 是否推广(默认否)
     */
    private Integer promotion;

    /**
     * 推广费率(默认0.05)
     */
    private BigDecimal promotionCosts;


    private Integer supplyChain;

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

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsSubclassId() {
        return goodsSubclassId;
    }

    public void setGoodsSubclassId(String goodsSubclassId) {
        this.goodsSubclassId = goodsSubclassId;
    }

    public String getGoodsSubclassName() {
        return goodsSubclassName;
    }

    public void setGoodsSubclassName(String goodsSubclassName) {
        this.goodsSubclassName = goodsSubclassName;
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

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
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

    public Integer getActivityCountDown() {
        return activityCountDown;
    }

    public void setActivityCountDown(Integer activityCountDown) {
        this.activityCountDown = activityCountDown;
    }

    public BigDecimal getActivityCountDownTime() {
        return activityCountDownTime;
    }

    public void setActivityCountDownTime(BigDecimal activityCountDownTime) {
        this.activityCountDownTime = activityCountDownTime;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public List<String> getGoodsImgs() {
        return goodsImgs;
    }

    public void setGoodsImgs(List<String> goodsImgs) {
        this.goodsImgs = goodsImgs;
    }

    public List<SysGoodsTipsVO> getSysGoodsTipsVOs() {
        return sysGoodsTipsVOs;
    }

    public void setSysGoodsTipsVOs(List<SysGoodsTipsVO> sysGoodsTipsVOs) {
        this.sysGoodsTipsVOs = sysGoodsTipsVOs;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public LocalDateTime getCollectLastValidTime() {
        return collectLastValidTime;
    }

    public void setCollectLastValidTime(LocalDateTime collectLastValidTime) {
        this.collectLastValidTime = collectLastValidTime;
    }

    public String getCollectInfo() {
        return collectInfo;
    }

    public void setCollectInfo(String collectInfo) {
        this.collectInfo = collectInfo;
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

    public Integer getGoodsSales() {
        return goodsSales;
    }

    public void setGoodsSales(Integer goodsSales) {
        this.goodsSales = goodsSales;
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

    public BigDecimal getCollectPrice() {
        return collectPrice;
    }

    public void setCollectPrice(BigDecimal collectPrice) {
        this.collectPrice = collectPrice;
    }

    @Override
    public String toString() {
        return "SysGoodsDetailVO{" +
                "id='" + id + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                ", goodsTypeId='" + goodsTypeId + '\'' +
                ", goodsTypeName='" + goodsTypeName + '\'' +
                ", goodsSubclassId='" + goodsSubclassId + '\'' +
                ", goodsSubclassName='" + goodsSubclassName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsRetailPrice=" + goodsRetailPrice +
                ", goodsCostPrice=" + goodsCostPrice +
                ", goodsNum=" + goodsNum +
                ", goodsSurplusNum=" + goodsSurplusNum +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", approvalFlag=" + approvalFlag +
                ", limitBuyFlag=" + limitBuyFlag +
                ", limitBuyNum=" + limitBuyNum +
                ", activityCountDown=" + activityCountDown +
                ", activityCountDownTime=" + activityCountDownTime +
                ", useCountDown=" + useCountDown +
                ", useCountDownTime=" + useCountDownTime +
                ", orderNum=" + orderNum +
                ", content='" + content + '\'' +
                ", stickStatus=" + stickStatus +
                ", stickWeight=" + stickWeight +
                ", goodsViewUrl='" + goodsViewUrl + '\'' +
                ", createTime=" + createTime +
                ", goodsImgs=" + goodsImgs +
                ", sysGoodsTipsVOs=" + sysGoodsTipsVOs +
                ", firstShowImg='" + firstShowImg + '\'' +
                ", collectFlag=" + collectFlag +
                ", collectNum=" + collectNum +
                ", collectLastValidTime=" + collectLastValidTime +
                ", collectPrice=" + collectPrice +
                ", collectInfo='" + collectInfo + '\'' +
                ", lastValidTime=" + lastValidTime +
                ", putawayTime=" + putawayTime +
                ", goodsSales=" + goodsSales +
                ", writeOffStartTime=" + writeOffStartTime +
                ", writeOffEndTime=" + writeOffEndTime +
                '}';
    }

    public Integer getSupplyChain() {
        return supplyChain;
    }

    public void setSupplyChain(Integer supplyChain) {
        this.supplyChain = supplyChain;
    }
}
