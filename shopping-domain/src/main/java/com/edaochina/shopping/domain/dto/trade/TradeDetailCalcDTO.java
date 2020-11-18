package com.edaochina.shopping.domain.dto.trade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TradeDetailCalcDTO implements Serializable {

    @ApiModelProperty("分润记录id")
    private String id;

}
