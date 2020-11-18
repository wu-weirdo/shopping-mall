package com.edaochina.shopping.domain.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jintian
 * @date 2019/11/5 19:15
 */
@Data
public class ShareOrderDetail {

    private String merchantName;

    private String merchantId;

    private String goodsName;

    private String goodsId;

    private String shareMerchantId;

    private String orderId;

    private String shareFeeDetail;

    private Integer buyNum;

    private BigDecimal shareRate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private BigDecimal orderPrice;

    private Integer status;
}
