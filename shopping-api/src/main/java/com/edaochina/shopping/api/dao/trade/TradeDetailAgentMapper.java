package com.edaochina.shopping.api.dao.trade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edaochina.shopping.domain.dto.trade.TradeDetailAgentDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailAgent;

import java.math.BigDecimal;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
public interface TradeDetailAgentMapper extends BaseMapper<TradeDetailAgent> {

    /**
     * 查询本月盈利
     *
     * @param dto
     * @return
     */
    BigDecimal getCurrMonthProfit(TradeDetailAgentDTO dto);

    /**
     * 查询当天收入
     *
     * @param dto
     * @return
     */
    BigDecimal getTodayInCome(TradeDetailAgentDTO dto);

    /**
     * 查询本周收入
     *
     * @param dto
     * @return
     */
    BigDecimal getCurrWeekInCome(TradeDetailAgentDTO dto);

    /**
     * 查询本月收入
     *
     * @param dto
     * @return
     */
    BigDecimal getCurrMonthInCome(TradeDetailAgentDTO dto);

    /**
     * 查询本年收入
     *
     * @param dto
     * @return
     */
    BigDecimal getCurrYearInCome(TradeDetailAgentDTO dto);

}
