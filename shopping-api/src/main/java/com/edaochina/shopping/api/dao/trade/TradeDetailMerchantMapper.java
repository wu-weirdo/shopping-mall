package com.edaochina.shopping.api.dao.trade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.trade.TradeDetailMerchantDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailMerchant;

import java.math.BigDecimal;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
public interface TradeDetailMerchantMapper extends BaseMapper<TradeDetailMerchant> {

    /**
     * 查询本月盈利
     *
     * @param dto
     * @return
     */
    BigDecimal getCurrMonthProfit(TradeDetailMerchantDTO dto);

    /**
     * 查询当天收入
     *
     * @param dto
     * @return
     */
    BigDecimal getTodayInCome(TradeDetailMerchantDTO dto);

    /**
     * 查询本周收入
     *
     * @param dto
     * @return
     */
    BigDecimal getCurrWeekInCome(TradeDetailMerchantDTO dto);

    /**
     * 查询本月收入
     *
     * @param dto
     * @return
     */
    BigDecimal getCurrMonthInCome(TradeDetailMerchantDTO dto);

    /**
     * 查询本年收入
     *
     * @param dto
     * @return
     */
    BigDecimal getCurrYearInCome(TradeDetailMerchantDTO dto);
}
