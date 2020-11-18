package com.edaochina.shopping.domain.vo.trade;

import java.util.List;

/**
 * @author jintian
 * @date 2019/4/23 13:49
 */
public class AppShoppingCartInfo {

    private String shopId;

    private String shopName;

    private Integer expiredFlag;

    private List<ShoppingCartInfoVo> goodsList;

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

    public List<ShoppingCartInfoVo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ShoppingCartInfoVo> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getExpiredFlag() {
        return expiredFlag;
    }

    public void setExpiredFlag(Integer expiredFlag) {
        this.expiredFlag = expiredFlag;
    }
}
