package com.edaochina.shopping.domain.vo.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jintian
 * @date 2019/11/14 18:17
 */
@Data
public class SysChainProfitVo {

    private Integer id;

    private String orderId;

    private String merchantId;

    private String goodsName;

    private Integer buyNum;

    private BigDecimal orderPrice;

    private BigDecimal chainFree;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime profitTime;

    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
