package com.edaochina.shopping.domain.dto.goods;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class GoodsTypeDTO implements Serializable {
    @ApiModelProperty("商品类型id")
    private String id;
    @ApiModelProperty("商品类型名称")
    private String typeName;
    @ApiModelProperty("商品子类列表")
    private List<GoodsSubclassDTO> goodsSubclassDTOS;

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

    public List<GoodsSubclassDTO> getGoodsSubclassDTOS() {
        return goodsSubclassDTOS;
    }

    public void setGoodsSubclassDTOS(List<GoodsSubclassDTO> goodsSubclassDTOS) {
        this.goodsSubclassDTOS = goodsSubclassDTOS;
    }
}
