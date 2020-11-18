package com.edaochina.shopping.api.service.trade.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.trade.TradeDetailAgentMapper;
import com.edaochina.shopping.api.service.trade.TradeDetailAgentService;
import com.edaochina.shopping.common.utils.date.LocalDateTimeUtil;
import com.edaochina.shopping.domain.dto.trade.TradeDetailAgentDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailAgent;
import com.edaochina.shopping.domain.vo.trade.TradeDetailAgentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
@Service
public class TradeDetailAgentServiceImpl extends ServiceImpl<TradeDetailAgentMapper, TradeDetailAgent> implements TradeDetailAgentService {

    @Resource
    TradeDetailAgentMapper mapper;

    /**
     * app代理商登陆之后的交易数据分析
     *
     * @param dto
     * @return
     */
    @Override
    public TradeDetailAgentVO dataStatis(TradeDetailAgentDTO dto) {
        TradeDetailAgentVO vo = new TradeDetailAgentVO();
        dto.setStartTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getBeginDayOfMonth()));
        dto.setEndTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getEndDayOfMonth()));
        BigDecimal currMonthProfit = mapper.getCurrMonthProfit(dto);
        dto.setStartTime(LocalDateTimeUtil.getStartTimeOfToday());
        dto.setEndTime(LocalDateTimeUtil.getEndTimeOfToday());
        BigDecimal todayInCome = mapper.getTodayInCome(dto);
        dto.setStartTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getBeginDayOfWeek()));
        dto.setEndTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getEndDayOfWeek()));
        BigDecimal currWeekInCome = mapper.getCurrWeekInCome(dto);
        dto.setStartTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getBeginDayOfMonth()));
        dto.setEndTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getEndDayOfMonth()));
        BigDecimal currMonthInCome = mapper.getCurrMonthInCome(dto);
        dto.setStartTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getBeginDayOfYear()));
        dto.setEndTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getEndDayOfYear()));
        BigDecimal currYearInCome = mapper.getCurrYearInCome(dto);
        vo.setCurrMonthProfit(currMonthProfit);
        vo.setTodayInCome(todayInCome);
        vo.setCurrWeekInCome(currWeekInCome);
        vo.setCurrMonthInCome(currMonthInCome);
        vo.setCurrYearInCome(currYearInCome);
        return vo;
    }
}
