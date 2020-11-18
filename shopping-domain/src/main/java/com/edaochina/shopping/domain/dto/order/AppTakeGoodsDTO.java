package com.edaochina.shopping.domain.dto.order;

import com.edaochina.shopping.domain.base.page.Pages;

/**
 * 小程序用户提货单
 */
public class AppTakeGoodsDTO {

    /**
     * 商家id
     */
    private String shoperId;

    /**
     * 用户id
     */
    private String userId;

    private Pages pages;

    public String getShoperId() {
        return shoperId;
    }

    public void setShoperId(String shoperId) {
        this.shoperId = shoperId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }
}
