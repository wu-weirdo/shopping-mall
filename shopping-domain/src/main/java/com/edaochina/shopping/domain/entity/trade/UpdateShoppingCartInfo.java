package com.edaochina.shopping.domain.entity.trade;

import java.util.List;

/**
 * @author Administrator
 * @date 2019-4-23 20:31
 */
public class UpdateShoppingCartInfo {

    private String userId;

    private String openId;

    private List<ShoppingCartInfo> shoppingCarts;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public List<ShoppingCartInfo> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCartInfo> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }
}
