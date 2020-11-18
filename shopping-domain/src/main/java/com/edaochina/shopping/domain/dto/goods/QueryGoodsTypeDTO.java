package com.edaochina.shopping.domain.dto.goods;

import com.edaochina.shopping.domain.base.page.Pages;

import java.io.Serializable;
import java.util.Date;

public class QueryGoodsTypeDTO implements Serializable {

    private String typeName;

    private Date startTime;

    private Date endTime;

    private Pages pages;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }
}
