package com.edaochina.shopping.domain.vo.goods;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GoodsSubclassVO implements Serializable {
    @ApiModelProperty("商品子类id")
    private String id;
    @ApiModelProperty("商品子类名称")
    private String subclassName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubclassName() {
        return subclassName;
    }

    public void setSubclassName(String subclassName) {
        this.subclassName = subclassName;
    }
}
