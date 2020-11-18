package com.edaochina.shopping.domain.dto.trade;

import lombok.Data;

/**
 * @author jintian
 * @date 2019/11/13 16:04
 */
@Data
public class ChainPayInfoAffirmDto {

    private Integer id;

    private Integer payStatus;

    private String refuseReason;
}
