package com.edaochina.shopping.domain.dto.trade;

import com.edaochina.shopping.domain.base.page.Pages;

public class ShoppingCartDTO {

    private String userId;

    private String longitude = "0";

    private String latitude = "0";

    private Pages pages;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }
}
