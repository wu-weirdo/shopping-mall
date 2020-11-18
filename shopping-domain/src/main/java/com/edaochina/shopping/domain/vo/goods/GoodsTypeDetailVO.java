package com.edaochina.shopping.domain.vo.goods;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class GoodsTypeDetailVO implements Serializable {

    private String id;

    private String typeName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private List<GoodsSubclassVO> goodsSubclassVOS;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public List<GoodsSubclassVO> getGoodsSubclassVOS() {
        return goodsSubclassVOS;
    }

    public void setGoodsSubclassVOS(List<GoodsSubclassVO> goodsSubclassVOS) {
        this.goodsSubclassVOS = goodsSubclassVOS;
    }
}
