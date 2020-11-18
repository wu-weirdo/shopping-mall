package com.edaochina.shopping.domain.vo.user;

import java.math.BigDecimal;

/**
 * PromotionInfoVO
 *
 * @author wangpenglei
 * @since 2019/11/6 16:03
 */
public class PromotionInfoVO {

    private int userCount;

    private int goodsCount;

    private int promotionCount;

    private BigDecimal promotionPriceSum;

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public int getPromotionCount() {
        return promotionCount;
    }

    public void setPromotionCount(int promotionCount) {
        this.promotionCount = promotionCount;
    }

    public BigDecimal getPromotionPriceSum() {
        return promotionPriceSum;
    }

    public void setPromotionPriceSum(BigDecimal promotionPriceSum) {
        this.promotionPriceSum = promotionPriceSum;
    }
}
