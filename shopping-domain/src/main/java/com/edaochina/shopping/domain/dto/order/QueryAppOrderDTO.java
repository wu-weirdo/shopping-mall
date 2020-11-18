package com.edaochina.shopping.domain.dto.order;

import com.edaochina.shopping.domain.base.page.Pages;

import java.io.Serializable;

/**
 * @author 24h
 */
public class QueryAppOrderDTO implements Serializable {

    private String id;

    private String userId;

    private String origin;

    private Integer status;

    private Pages pages;

    @Override
    public String toString() {
        return "QueryAppOrderDTO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", origin='" + origin + '\'' +
                ", status=" + status +
                ", pages=" + pages +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
