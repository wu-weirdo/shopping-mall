package com.edaochina.shopping.domain.dto.order;

import com.edaochina.shopping.domain.base.page.Pages;

/**
 * @author jintian
 * @date 2019/6/27 16:48
 */
public class AppCollectOrderDTO {

    private String userId;

    private Integer orderStatus;

    private Pages pages;

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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
