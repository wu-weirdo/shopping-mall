package com.edaochina.shopping.domain.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("商家利润查询条件")
@Data
public class TradeDetailMerchantDTO implements Serializable {

    /**
     * 商家id
     */
    @ApiModelProperty("商家id")
    private String merchantId;

    @ApiModelProperty("查询的月份")
    private String month;

    /**
     * 查询条件-开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 查询条件-结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
