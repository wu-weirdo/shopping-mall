package com.edaochina.shopping.domain.vo.trade;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TradeDetailAgentVO implements Serializable {

    /**
     * 本月赢利
     */
    private BigDecimal currMonthProfit;

    /**
     * 本日收入
     */
    private BigDecimal todayInCome;

    /**
     * 本周收入
     */
    private BigDecimal currWeekInCome;

    /**
     * 本月收入
     */
    private BigDecimal currMonthInCome;

    /**
     * 本年收入
     */
    private BigDecimal currYearInCome;

    /**
     * 当日会员数
     */
    private Integer todayMermberNum;

    /**
     * 本周会员数
     */
    private Integer currWeekMermberNum;

    /**
     * 本月会员数
     */
    private Integer currMonthMermberNum;

    /**
     * 本年会员数
     */
    private Integer currYearMermberNum;

    /**
     * 推广收入
     */
    private BigDecimal shareProfit;

    /**
     * 渠道收入
     */
    private BigDecimal chainProfit;
}
