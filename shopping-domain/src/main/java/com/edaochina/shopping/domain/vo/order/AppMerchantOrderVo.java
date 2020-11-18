package com.edaochina.shopping.domain.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * app 商家订单列表展示内容
 *
 * @author jintian
 * @date 2019/5/8 17:08
 */
public class AppMerchantOrderVo {
    /**
     * 订单id
     */
    private String id;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 购买数量
     */
    private int buyNum;

    /**
     * 商品规格
     */
    private String goodsSpec;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 核销时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffTime;

    /**
     * 订单金额
     */
    private BigDecimal costPrice;

    /**
     * 是否是当日核销标识
     */
    private int todayWriteFlag;

    /**
     * 商户名称
     */
    private String shopName;

    private BigDecimal actualPrice;

    private Integer collectFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getWriteOffTime() {
        return writeOffTime;
    }

    public void setWriteOffTime(LocalDateTime writeOffTime) {
        this.writeOffTime = writeOffTime;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public int getTodayWriteFlag() {
        return todayWriteFlag;
    }

    public void setTodayWriteFlag(int todayWriteFlag) {
        this.todayWriteFlag = todayWriteFlag;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }
}
