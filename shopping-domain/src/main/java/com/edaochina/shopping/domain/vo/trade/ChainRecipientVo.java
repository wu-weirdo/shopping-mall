package com.edaochina.shopping.domain.vo.trade;

import com.edaochina.shopping.domain.entity.trade.ChainRecipientDetail;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jintian
 * @date 2019/11/13 15:47
 */
@Data
public class ChainRecipientVo extends ChainRecipientDetail {
    private String goodsName;

    private String goodsPrice;

    private Integer num;

    private String chamberlain;

    private String bank;

    private String cardNumber;
}
