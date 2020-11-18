package com.edaochina.shopping.api.service.trade.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edaochina.shopping.api.dao.trade.TradeDetailMerchantMapper;
import com.edaochina.shopping.api.service.trade.TradeDetailChainProfitService;
import com.edaochina.shopping.api.service.trade.TradeDetailMerchantService;
import com.edaochina.shopping.api.service.trade.TradeDetailShareProfitService;
import com.edaochina.shopping.common.utils.date.LocalDateTimeUtil;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.trade.SysChainGoodsProfitDTO;
import com.edaochina.shopping.domain.dto.trade.TradeDetailMerchantDTO;
import com.edaochina.shopping.domain.entity.trade.TradeDetailMerchant;
import com.edaochina.shopping.domain.vo.trade.SysChainProfitVo;
import com.edaochina.shopping.domain.vo.trade.TradeDetailMerchantVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
@Service
public class TradeDetailMerchantServiceImpl extends ServiceImpl<TradeDetailMerchantMapper, TradeDetailMerchant> implements TradeDetailMerchantService {

    @Resource
    TradeDetailMerchantMapper mapper;

    private final TradeDetailShareProfitService shareProfitService;
    private final TradeDetailChainProfitService chainProfitService;

    public TradeDetailMerchantServiceImpl(TradeDetailShareProfitService shareProfitService, TradeDetailChainProfitService chainProfitService) {
        this.shareProfitService = shareProfitService;
        this.chainProfitService = chainProfitService;
    }

    public static Pair<LocalDateTime, LocalDateTime> getLocalDateTimeLocalDateTimePair(String month) {
        LocalDate base = LocalDate.now();
        if (!org.springframework.util.StringUtils.isEmpty(month)) {
            base = LocalDate.parse(month + "01", DateTimeFormatter.BASIC_ISO_DATE);
        }
        return Pair.of(base.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(),
                base.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).atStartOfDay());
    }

    /**
     * app商家登陆后的商家交易数据分析
     *
     * @param dto
     * @return
     */
    @Override
    public TradeDetailMerchantVO dataStatis(TradeDetailMerchantDTO dto) {
        TradeDetailMerchantVO vo = new TradeDetailMerchantVO();
        // 兼容3.1.1
        if (StringUtils.isNotBlank(dto.getMonth())) {
            dto.setStartTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getBeginDayOfMonth(dto.getMonth().trim())));
            dto.setEndTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getEndDayOfMonth(dto.getMonth().trim())));
        } else {
            dto.setStartTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getBeginDayOfMonth()));
            dto.setEndTime(LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getEndDayOfMonth()));
        }

        vo.setShareProfit(shareProfitService.getShareByRoleAndUserId(RoleConstants.MERCHANT_ROLE, dto.getMerchantId(), dto.getStartTime(), dto.getEndTime()));

        BigDecimal currMonthProfit = mapper.getCurrMonthInCome(dto);

        dto.setStartTime(LocalDateTimeUtil.getStartTimeOfToday());
        dto.setEndTime(LocalDateTimeUtil.getEndTimeOfToday());
        BigDecimal todayInCome = mapper.getTodayInCome(dto);
        dto.setStartTime(LocalDateTimeUtil.dateToLocalDateTime(Objects.requireNonNull(LocalDateTimeUtil.getBeginDayOfWeek())));
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
        setChainProfit(vo, dto);
        return vo;
    }

    /**
     * 设置渠道收入
     *
     * @param vo  返回视图
     * @param dto 查询参数
     */
    private void setChainProfit(TradeDetailMerchantVO vo, TradeDetailMerchantDTO dto) {
        Pair<LocalDateTime, LocalDateTime> pair = parseMonth(dto.getMonth());
        SysChainGoodsProfitDTO chainGoodsProfitDTO = new SysChainGoodsProfitDTO();
        chainGoodsProfitDTO.setRoleId(RoleConstants.MERCHANT_ROLE_STRING);
        chainGoodsProfitDTO.setUserId(dto.getMerchantId());
        chainGoodsProfitDTO.setBeginTime(pair.getFirst());
        chainGoodsProfitDTO.setEndTime(pair.getSecond());

        BigDecimal chainProfit = chainProfitService.getChainProfitListApp(chainGoodsProfitDTO).getList().parallelStream()
                .map(SysChainProfitVo::getChainFree)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        vo.setChainProfit(chainProfit);
    }

    private Pair<LocalDateTime, LocalDateTime> parseMonth(String month) {
        return getLocalDateTimeLocalDateTimePair(month);
    }
}
