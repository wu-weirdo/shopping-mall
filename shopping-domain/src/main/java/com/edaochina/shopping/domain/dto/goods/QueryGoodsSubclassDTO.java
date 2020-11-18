package com.edaochina.shopping.domain.dto.goods;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class QueryGoodsSubclassDTO implements Serializable {
    @ApiModelProperty("商品类型id")
    private String typeId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
