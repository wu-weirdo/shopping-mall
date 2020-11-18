package com.edaochina.shopping.domain.dto.goods;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class DeleteGoodsDTO implements Serializable {
    @ApiModelProperty("被删除的商品id")
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
