package com.edaochina.shopping.domain.vo.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 分润展示内容
 *
 * @author jintian
 * @date 2019/11/5 16:02
 */
@Data
public class SysShareProfitVo {

    private Integer id;

    private String orderId;

    private String merchantId;

    private String shareMerchantId;

    private String goodsName;

    private Integer buyNum;

    /**
     * 1:拼团订单，2：供应链商品，3：爆品
     */
    private String goodsType;

    private BigDecimal orderPrice;

    private BigDecimal profitFree;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime profitTime;

    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
