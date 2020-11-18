package com.edaochina.shopping.domain.vo.goods;


import java.io.Serializable;

public class SysGoodsTipsVO implements Serializable {

    private String content;

    private Integer sort;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
