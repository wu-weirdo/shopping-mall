package com.edaochina.shopping.domain.base.page;


import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
    private List<T> list;
    private Pages pages;

    public PageResult() {
    }

    public PageResult(List<T> list, Pages pages) {
        this.list = list;
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }
}
