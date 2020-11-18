package com.edaochina.shopping.api.facade.profit.impl;

import com.edaochina.shopping.api.dao.sys.SysDictionaryParamMapper;
import com.edaochina.shopping.api.dao.user.CommunityMapper;
import com.edaochina.shopping.api.facade.profit.MemberOrderProfitFacade;
import com.edaochina.shopping.api.service.order.MemberOrderService;
import com.edaochina.shopping.api.service.sys.AreaAgentProfitService;
import com.edaochina.shopping.api.service.sys.MemberProfitRuleService;
import com.edaochina.shopping.api.service.trade.MemberOrderCalcDetailService;
import com.edaochina.shopping.api.service.trade.TradeDetailChainProfitService;
import com.edaochina.shopping.api.service.trade.TradeDetailShareProfitService;
import com.edaochina.shopping.api.service.user.SysUserAgentService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.utils.date.LocalDateTimeUtil;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.dto.trade.SysChainGoodsProfitDTO;
import com.edaochina.shopping.domain.dto.trade.TradeDetailAgentDTO;
import com.edaochina.shopping.domain.dto.trade.TradeDetailPartenerDTO;
import com.edaochina.shopping.domain.entity.order.MemberOrder;
import com.edaochina.shopping.domain.entity.sys.AreaAgentProfit;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import com.edaochina.shopping.domain.entity.user.SysUserAgent;
import com.edaochina.shopping.domain.vo.sys.SysHasAgenAreaVo;
import com.edaochina.shopping.domain.vo.trade.SysChainProfitVo;
import com.edaochina.shopping.domain.vo.trade.TradeDetailAgentVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.edaochina.shopping.api.service.trade.impl.TradeDetailMerchantServiceImpl.getLocalDateTimeLocalDateTimePair;

/**
 * @author jintian
 * @date 2019/4/2 10:39
 */
@Component
public class MemberOrderProfitFacadeImpl implements MemberOrderProfitFacade {

    private final MemberOrderService memberOrderService;
    private final MemberProfitRuleService memberProfitRuleService;
    @Resource
    CommunityMapper communityMapper;
    @Resource
    AreaAgentProfitService areaAgentProfitService;
    @Resource
    SysDictionaryParamMapper sysDictionaryParamMapper;
    @Resource
    SysUserMerchantService sysUserMerchantService;
    private final SysUserAgentService sysUserAgentService;
    @Resource
    MemberOrderCalcDetailService memberOrderCalcDetailService;
    private final TradeDetailShareProfitService shareProfitService;
    private final TradeDetailChainProfitService chainProfitService;


    private Logger logger = LoggerFactory.getLogger(MemberOrderProfitFacadeImpl.class);

    public MemberOrderProfitFacadeImpl(TradeDetailChainProfitService chainProfitService, MemberOrderService memberOrderService, MemberProfitRuleService memberProfitRuleService, SysUserAgentService sysUserAgentService, TradeDetailShareProfitService shareProfitService) {
        this.chainProfitService = chainProfitService;
        this.memberOrderService = memberOrderService;
        this.memberProfitRuleService = memberProfitRuleService;
        this.sysUserAgentService = sysUserAgentService;
        this.shareProfitService = shareProfitService;
    }

    @Override
    public void calcMemberInsertDetail() {
        int memberNum;
        logger.info("用户会员分润开始");
        SysDictionaryParam userBase = sysDictionaryParamMapper.querySysValue("member_profit", "base_user_member");
        SysDictionaryParam merchantBase = sysDictionaryParamMapper.querySysValue("member_profit", "base_merchant_member");
        SysDictionaryParam partenerUse = sysDictionaryParamMapper.querySysValue("member_profit", "partener_use_member");
        SysDictionaryParam partenerMerchant = sysDictionaryParamMapper.querySysValue("member_profit", "partener_merchant_member");
        do {
            // 计算普通会员分润
            List<MemberOrder> memberOrders = memberOrderService.selectMemberOrderByOrderType(1);
            memberOrders.forEach(memberOrder -> {
                memberProfitRuleService.userMemberCalc(memberOrder, userBase, partenerUse);
            });
            memberNum = memberOrders.size();
        } while (memberNum == 100);
        logger.info("用户会员分润结束");
        logger.info("商户会员分润开始");
        do {
            //  计算商家会员分润
            List<MemberOrder> memberOrders = memberOrderService.selectMemberOrderByOrderType(2);
            memberOrders.forEach(memberOrder -> {
                memberProfitRuleService.merchantMemberCalc(memberOrder, merchantBase, partenerMerchant);
            });
        } while (memberNum == 100);
        logger.info("商户会员分润结束");
    }

    @Override
    public void areaProfitCalculate() {
        // 判断该区域有无区域分润比例，没有查询插入
        List<SysHasAgenAreaVo> sysHasAgenAreaVos = communityMapper.queryDistinctArea();
        SysDictionaryParam userBase = sysDictionaryParamMapper.querySysValue("member_profit", "base_user_member");
        SysDictionaryParam merchantBase = sysDictionaryParamMapper.querySysValue("member_profit", "base_merchant_member");
        SysDictionaryParam supplementLimit = sysDictionaryParamMapper.querySysValue("member_profit", "supplement_num");
        SysDictionaryParam supplementUser = sysDictionaryParamMapper.querySysValue("member_profit", "supplement_user_member");
        SysDictionaryParam supplementMerchant = sysDictionaryParamMapper.querySysValue("member_profit", "supplement_merchant_member");

        sysHasAgenAreaVos.forEach(sysHasAgenAreaVo -> {
            // 判断该区域是否达到了最大标准，没有则判断是否满足最大标准
            AreaAgentProfit areaAgentProfit = areaAgentProfitService.selectAreaAgentProfitByCountyId(sysHasAgenAreaVo.getCountyCode(), 2);
            if (areaAgentProfit == null) {
                areaAgentProfitService.insertAreaAgentProfit(new AreaAgentProfit(new BigDecimal(userBase.getSysValue()), sysHasAgenAreaVo.getCountyCode(), 1));
                areaAgentProfitService.insertAreaAgentProfit(new AreaAgentProfit(new BigDecimal(merchantBase.getSysValue()), sysHasAgenAreaVo.getCountyCode(), 2));
                return;
            }
            // 如果达到了最大的分润比例
            if (areaAgentProfit.getSupplementStatus() == 1) {
                return;
            }
            // 查询区县下商户会员数
            int num = sysUserMerchantService.queryMemberNumByCountId(sysHasAgenAreaVo.getCityCode());
            // 判断是否达到了指定商户数
            if (num >= Integer.parseInt(supplementLimit.getSysValue())) {
                AreaAgentProfit mechartProfit = new AreaAgentProfit(new BigDecimal(supplementMerchant.getSysValue()), sysHasAgenAreaVo.getCountyCode(), 2);
                mechartProfit.setSupplementStatus(1);
                areaAgentProfitService.updateProfitRatioByCountId(mechartProfit);
                AreaAgentProfit userProfit = new AreaAgentProfit(new BigDecimal(supplementUser.getSysValue()), sysHasAgenAreaVo.getCountyCode(), 1);
                mechartProfit.setSupplementStatus(1);
                areaAgentProfitService.updateProfitRatioByCountId(userProfit);
                // 额外分润
                memberProfitRuleService.supplementProfit(sysHasAgenAreaVo.getCountyCode()
                        , new BigDecimal(supplementUser.getSysValue()).subtract(new BigDecimal(userBase.getSysValue()))
                        , new BigDecimal(supplementMerchant.getSysValue()).subtract(new BigDecimal(merchantBase.getSysValue())));
            }
        });
    }

    @Override
    public AjaxResult dataStatis(TradeDetailAgentDTO dto) {
        TradeDetailAgentVO vo = new TradeDetailAgentVO();
        vo.setCurrWeekInCome(memberOrderCalcDetailService.getAgentCalcCountInfo(dto.getAgentId(), LocalDateTimeUtil.getBeginDayOfWeek(), LocalDateTimeUtil.getEndDayOfWeek()));
        vo.setCurrMonthInCome(memberOrderCalcDetailService.getAgentCalcCountInfo(dto.getAgentId(), LocalDateTimeUtil.getBeginDayOfMonth(), LocalDateTimeUtil.getEndDayOfMonth()));
        vo.setCurrYearInCome(memberOrderCalcDetailService.getAgentCalcCountInfo(dto.getAgentId(), LocalDateTimeUtil.getBeginDayOfYear(), LocalDateTimeUtil.getEndDayOfYear()));
        // 根据月份查询
        // 兼容3.1.1
        if (StringUtils.isNotBlank(dto.getMonth())) {
            vo.setCurrMonthProfit(memberOrderCalcDetailService.getAgentCalcCountInfo(dto.getAgentId(), LocalDateTimeUtil.getBeginDayOfMonth(dto.getMonth().trim()), LocalDateTimeUtil.getEndDayOfMonth(dto.getMonth().trim())));
        } else {
            vo.setCurrMonthProfit(memberOrderCalcDetailService.getAgentCalcCountInfo(dto.getAgentId(), LocalDateTimeUtil.getBeginDayOfMonth(), LocalDateTimeUtil.getEndDayOfMonth()));
        }

        SysUserAgent userAgent = sysUserAgentService.getById(dto.getAgentId());
        // 设置预计本日收入
        SysDictionaryParam userBase = sysDictionaryParamMapper.querySysValue("member_profit", "base_user_member");
        SysDictionaryParam merchantBase = sysDictionaryParamMapper.querySysValue("member_profit", "base_merchant_member");

        BigDecimal anticipatedTodayInCome = memberOrderService.anticipatedAgentTodayInCome(userAgent.getId(), userBase.getSysValue(), merchantBase.getSysValue());
        vo.setTodayInCome(anticipatedTodayInCome);
        if (StringUtils.isNotBlank(dto.getMonth())) {
            vo.setShareProfit(shareProfitService.getShareByRoleAndUserId(RoleConstants.AGENT_ROLE, dto.getAgentId()
                    , LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getBeginDayOfMonth(dto.getMonth().trim()))
                    , LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getEndDayOfMonth(dto.getMonth().trim()))));
        } else {
            vo.setShareProfit(shareProfitService.getShareByRoleAndUserId(RoleConstants.AGENT_ROLE, dto.getAgentId()
                    , LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getBeginDayOfMonth())
                    , LocalDateTimeUtil.dateToLocalDateTime(LocalDateTimeUtil.getEndDayOfMonth())));
        }
        // 查询会员数
        int todayNum = memberOrderService.queryMermberNum(LocalDateTimeUtil.getBeginTimeOfToday(), dto.getAgentId());
        int weekNum = memberOrderService.queryMermberNum(LocalDateTimeUtil.getBeginDayOfWeek(), dto.getAgentId());
        int monthNum = memberOrderService.queryMermberNum(LocalDateTimeUtil.getBeginDayOfMonth(), dto.getAgentId());
        int yearNum = memberOrderService.queryMermberNum(LocalDateTimeUtil.getBeginDayOfYear(), dto.getAgentId());
        vo.setTodayMermberNum(todayNum);
        vo.setCurrWeekMermberNum(weekNum);
        vo.setCurrMonthMermberNum(monthNum);
        vo.setCurrYearMermberNum(yearNum);
        if (vo.getCurrMonthInCome() == null) {
            vo.setCurrMonthInCome(new BigDecimal(0.00));
        }
        if (vo.getCurrMonthProfit() == null) {
            vo.setCurrMonthProfit(new BigDecimal(0.00));
        }
        if (vo.getCurrWeekInCome() == null) {
            vo.setCurrWeekInCome(new BigDecimal(0.00));
        }
        if (vo.getCurrYearInCome() == null) {
            vo.setCurrYearInCome(new BigDecimal(0.00));
        }
        if (vo.getTodayInCome() == null) {
            vo.setTodayInCome(new BigDecimal(0.00));
        }
        setChainProfit(vo, dto);
        return new AjaxResult(vo);
    }

    /**
     * 设置渠道收入
     *
     * @param vo  返回视图
     * @param dto 查询参数
     */
    private void setChainProfit(TradeDetailAgentVO vo, TradeDetailAgentDTO dto) {
        Pair<LocalDateTime, LocalDateTime> pair = parseMonth(dto);

        SysChainGoodsProfitDTO chainGoodsProfitDTO = new SysChainGoodsProfitDTO();
        chainGoodsProfitDTO.setRoleId(RoleConstants.AGENT_ROLE_STRING);
        chainGoodsProfitDTO.setUserId(dto.getAgentId());
        chainGoodsProfitDTO.setBeginTime(pair.getFirst());
        chainGoodsProfitDTO.setEndTime(pair.getSecond());

        BigDecimal chainProfit = chainProfitService.getChainProfitListApp(chainGoodsProfitDTO).getList().parallelStream()
                .map(SysChainProfitVo::getChainFree)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        vo.setChainProfit(chainProfit);
    }

    private Pair<LocalDateTime, LocalDateTime> parseMonth(TradeDetailAgentDTO dto) {
        return getLocalDateTimeLocalDateTimePair(dto.getMonth());
    }

    @Override
    public AjaxResult partenerDataStatis(TradeDetailPartenerDTO dto) {
        //  查询群社合伙利润信息
        TradeDetailAgentVO vo = new TradeDetailAgentVO();
        vo.setCurrWeekInCome(memberOrderCalcDetailService.getPartenerCalcCountInfo(dto.getPartenerId(), LocalDateTimeUtil.getBeginDayOfWeek(), LocalDateTimeUtil.getEndDayOfWeek()));
        vo.setCurrMonthInCome(memberOrderCalcDetailService.getPartenerCalcCountInfo(dto.getPartenerId(), LocalDateTimeUtil.getBeginDayOfMonth(), LocalDateTimeUtil.getEndDayOfMonth()));
        vo.setCurrYearInCome(memberOrderCalcDetailService.getPartenerCalcCountInfo(dto.getPartenerId(), LocalDateTimeUtil.getBeginDayOfYear(), LocalDateTimeUtil.getEndDayOfYear()));
        // 根据月份查询
        // 兼容3.1.1
        if (StringUtils.isNotBlank(dto.getMonth())) {
            vo.setCurrMonthProfit(memberOrderCalcDetailService.getPartenerCalcCountInfo(dto.getPartenerId(), LocalDateTimeUtil.getBeginDayOfMonth(dto.getMonth().trim()), LocalDateTimeUtil.getEndDayOfMonth(dto.getMonth().trim())));
        } else {
            vo.setCurrMonthProfit(memberOrderCalcDetailService.getPartenerCalcCountInfo(dto.getPartenerId(), LocalDateTimeUtil.getBeginDayOfMonth(), LocalDateTimeUtil.getEndDayOfMonth()));
        }

        // 设置预计本日收入
        SysDictionaryParam partenerUse = sysDictionaryParamMapper.querySysValue("member_profit", "partener_use_member");
        SysDictionaryParam partenerMerchant = sysDictionaryParamMapper.querySysValue("member_profit", "partener_merchant_member");

        BigDecimal anticipatedTodayInCome = memberOrderService.anticipatedPartenerTodayInCome(dto.getPartenerId(), partenerUse.getSysValue(), partenerMerchant.getSysValue());
        vo.setTodayInCome(anticipatedTodayInCome);


        // 查询会员数
        int todayNum = memberOrderService.queryPartenerMermberNum(LocalDateTimeUtil.getBeginTimeOfToday(), dto.getPartenerId());
        int weekNum = memberOrderService.queryPartenerMermberNum(LocalDateTimeUtil.getBeginDayOfWeek(), dto.getPartenerId());
        int monthNum = memberOrderService.queryPartenerMermberNum(LocalDateTimeUtil.getBeginDayOfMonth(), dto.getPartenerId());
        int yearNum = memberOrderService.queryPartenerMermberNum(LocalDateTimeUtil.getBeginDayOfYear(), dto.getPartenerId());
        vo.setTodayMermberNum(todayNum);
        vo.setCurrWeekMermberNum(weekNum);
        vo.setCurrMonthMermberNum(monthNum);
        vo.setCurrYearMermberNum(yearNum);
        if (vo.getCurrMonthInCome() == null) {
            vo.setCurrMonthInCome(new BigDecimal(0.00));
        }
        if (vo.getCurrMonthProfit() == null) {
            vo.setCurrMonthProfit(new BigDecimal(0.00));
        }
        if (vo.getCurrWeekInCome() == null) {
            vo.setCurrWeekInCome(new BigDecimal(0.00));
        }
        if (vo.getCurrYearInCome() == null) {
            vo.setCurrYearInCome(new BigDecimal(0.00));
        }
        if (vo.getTodayInCome() == null) {
            vo.setTodayInCome(new BigDecimal(0.00));
        }
        return new AjaxResult(vo);
    }
}
