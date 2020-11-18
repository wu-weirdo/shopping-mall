package com.edaochina.shopping.domain.dto.order;

import com.edaochina.shopping.domain.base.page.Pages;

public class AppMerchantRefundOrderDTO {

    private String shoperId;

    /**
     * 退款订单状态（0-待处理，1-已退款，2-已拒绝）
     */
    private String paramStatus;

    /**
     * 搜索内容
     */
    private String search;

    private Pages pages = new Pages();

    public String getShoperId() {
        return shoperId;
    }

    public void setShoperId(String shoperId) {
        this.shoperId = shoperId;
    }

    public String getParamStatus() {
        return paramStatus;
    }

    public void setParamStatus(String paramStatus) {
        this.paramStatus = paramStatus;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }
}
