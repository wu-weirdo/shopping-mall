package com.edaochina.shopping.domain.dto.trade;

import com.edaochina.shopping.domain.base.page.Pages;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jintian
 * @date 2019/11/13 15:30
 */
@Data
public class ChainRecipientDto {

    private Pages pages;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Integer payStatus;

    private Integer shipmentsStatus;

    private String userId;

    private String goodsId;

    private String chainId;

    private String merchantId;

}