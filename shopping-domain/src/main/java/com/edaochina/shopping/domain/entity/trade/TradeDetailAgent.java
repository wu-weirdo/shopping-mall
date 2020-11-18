package com.edaochina.shopping.domain.entity.trade;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TradeDetailAgent extends Model<TradeDetailAgent> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 订单号（自用）
     */
    private String outTradeNo;

    /**
     * 微信交易流水号
     */
    private String transactionId;

    /**
     * 支付对象的名称
     */
    private String payObject;

    /**
     * 分润金额
     */
    private BigDecimal profitMoney;

    /**
     * 代理id
     */
    private String agentId;

    /**
     * 代理名称
     */
    private String agentName;

    private String payUserId;

    private String payUserName;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
