package com.edaochina.shopping.domain.dto.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("代理商盈利查询条件")
@Data
public class TradeDetailAgentDTO implements Serializable {

    /**
     * 代理id
     */
    @ApiModelProperty("代理商id")
    private String agentId;

    @ApiModelProperty("需要查询的月份")
    private String month;

    /**
     * 查询条件-开始时间
     */
    private LocalDateTime startTime;

    /**
     * 查询条件-结束时间
     */
    private LocalDateTime endTime;
}
