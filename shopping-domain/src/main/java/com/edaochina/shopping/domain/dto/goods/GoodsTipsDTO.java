package com.edaochina.shopping.domain.dto.goods;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xww
 * @since 2018-12-26
 */
public class GoodsTipsDTO implements Serializable {

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
