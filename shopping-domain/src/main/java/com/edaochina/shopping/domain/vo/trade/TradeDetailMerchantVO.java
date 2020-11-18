package com.edaochina.shopping.domain.vo.trade;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TradeDetailMerchantVO implements Serializable {

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
     * 推广收入
     */
    private BigDecimal shareProfit;

    /**
     * 渠道收入
     */
    private BigDecimal chainProfit;

}
