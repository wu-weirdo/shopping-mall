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
 * 分润计算表
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TradeDetailCalc extends Model<TradeDetailCalc> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 订单号（自用）
     */
    private String outTradeNo;

    /**
     * 交易流水号（微信）
     */
    private String transactionId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 成本价
     */
    private BigDecimal cost;

    /**
     * 利润
     */
    private BigDecimal profit;

    /**
     * 运送费用
     */
    private BigDecimal deliveryFee;

    /**
     * 计算时的分润规则id
     */
    private BigDecimal ruleId;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商家分润
     */
    private BigDecimal merchantProfit;

    /**
     * 来源商家id
     */
    private String sourceMerchantId;

    /**
     * 来源商家名称
     */
    private String sourceMerchantName;

    /**
     * 来源商家分润
     */
    private BigDecimal sourceMerchantProfit;

    /**
     * 团长（骑手）id
     */
    private String riderId;

    /**
     * 团长（骑手）名称
     */
    private String riderName;

    /**
     * 团长（骑手）分润
     */
    private BigDecimal riderProfit;

    /**
     * 代理商id
     */
    private String agentId;

    /**
     * 代理商姓名
     */
    private String agentName;

    /**
     * 代理商分润
     */
    private BigDecimal agentProfit;

    /**
     * 平台分润
     */
    private BigDecimal platformProfit;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
