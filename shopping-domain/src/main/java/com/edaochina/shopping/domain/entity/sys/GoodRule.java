package com.edaochina.shopping.domain.entity.sys;

import java.time.LocalDateTime;

/**
 * 商品规则
 *
 * @author jintian
 * @date 2019/9/17 17:35
 */
public class GoodRule {

    private Integer id;

    private String context;

    private Integer type;

    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
