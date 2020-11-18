package com.edaochina.shopping.domain.dto.order;

/**
 * @author jintian
 * @date 2019/4/18 11:03
 */
public class ShoppingGoods {
    private Integer shoppingCartId;

    private Integer buyNum;

    private String goodsId;

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
