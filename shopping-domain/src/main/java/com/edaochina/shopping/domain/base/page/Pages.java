package com.edaochina.shopping.domain.base.page;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页信息
 *
 * @author hjw
 */
public class Pages implements Serializable {
    /**
     * 页码
     */
    @ApiModelProperty("查询页")
    private int pageIndex;
    /**
     * 数量
     */
    @ApiModelProperty("每页大小")
    private int pageSize = 20;
    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private int total;

    /**
     * 起始页
     */
    @ApiModelProperty("起始条数")
    private int begIndex;

    @Override
    public String toString() {
        return "Pages{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", begIndex=" + begIndex +
                '}';
    }

    public Pages() {
    }

    public Pages(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public void incrementPageIndex() {
        pageIndex++;
    }

    public int getBegIndex() {
        begIndex = (pageIndex - 1) * pageSize;
        begIndex = (begIndex >= 0) ? begIndex : 0;
        return begIndex;
    }

    public void setBegIndex(int begIndex) {
        this.begIndex = begIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
