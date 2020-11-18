package com.edaochina.shopping.domain.dto.trade;

import lombok.Data;

/**
 * @author jintian
 * @date 2019/11/13 15:14
 */
@Data
public class ChainPayInfo {

    private Integer id;

    private String payImage;

    private String recipients;

    private String consigneeAddress;

    private String receiptNumber;

    private String remark;
}
