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
public class TradeDetailMerchant extends Model<TradeDetailMerchant> {

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
     * 支付对象
     */
    private String payObject;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 分润金额
     */
    private BigDecimal profitMoney;

    /**
     * 总应得（成本+分润）
     */
    private BigDecimal totalIncome;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    private String payUserId;

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

    /**
     * 备注
     */
    private String remark;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
