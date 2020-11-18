package com.edaochina.shopping.domain.dto.trade;

import com.edaochina.shopping.domain.base.page.Pages;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TradeDetailCalcListDTO implements Serializable {

    @ApiModelProperty("分润记录id")
    private String id;

    /**
     * 订单号（自用）
     */
    @ApiModelProperty("商户号")
    private String outTradeNo;

    /**
     * 商家id
     */
    @ApiModelProperty("商家id")
    private String merchantId;

    /**
     * 代理商id
     */
    @ApiModelProperty("代理商id")
    private String agentId;

    /**
     * 分页数据
     */
    private Pages pages;

    /**
     * 查询条件的开始时间
     */
    @ApiModelProperty("查询开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 查询条件的结束时间
     */
    @ApiModelProperty("查询结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
