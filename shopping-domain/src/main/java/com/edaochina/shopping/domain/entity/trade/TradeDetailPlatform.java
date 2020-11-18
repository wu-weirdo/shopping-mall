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
 * 平台交易明细
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TradeDetailPlatform extends Model<TradeDetailPlatform> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 订单号（自用）
     */
    private String outTradeNo;

    /**
     * 微信交易单号
     */
    private String transactionId;

    /**
     * 支付对象名称
     */
    private String payObject;

    /**
     * 分润金额
     */
    private BigDecimal profitMoney;

    /**
     * 支付用户id
     */
    private String payUserId;

    /**
     * 支付用户名称
     */
    private String payUserName;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 核销时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeOffTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
