package com.edaochina.shopping.api.service.sys.impl;

import com.edaochina.shopping.api.service.order.MemberOrderService;
import com.edaochina.shopping.api.service.sys.AreaAgentProfitService;
import com.edaochina.shopping.api.service.sys.MemberProfitRuleService;
import com.edaochina.shopping.api.service.trade.MemberOrderCalcDetailService;
import com.edaochina.shopping.api.service.user.SysCommunityPartnerService;
import com.edaochina.shopping.api.service.user.SysUserAgentService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.domain.entity.order.MemberOrder;
import com.edaochina.shopping.domain.entity.sys.AreaAgentProfit;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import com.edaochina.shopping.domain.entity.trade.MemberOrderCalcDetail;
import com.edaochina.shopping.domain.entity.user.SysUserAgent;
import com.edaochina.shopping.domain.entity.user.SysUserMerchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MemberProfitRuleServiceImpl implements MemberProfitRuleService {

    @Resource
    SysUserMerchantService sysUserMerchantService;
    @Resource
    SysUserAgentService sysUserAgentService;
    @Resource
    MemberOrderCalcDetailService memberOrderCalcDetailService;
    @Resource
    MemberOrderService memberOrderService;
    @Resource
    AreaAgentProfitService areaAgentProfitService;
    @Resource
    SysCommunityPartnerService sysCommunityPartnerService;

    private Logger logger = LoggerFactory.getLogger(MemberProfitRuleServiceImpl.class);

    @Override
    @Transactional
    public void merchantMemberCalc(MemberOrder memberOrder, SysDictionaryParam baseProfit, SysDictionaryParam partenerProfit) {
        String merchantId = memberOrder.getUserId();
        // 获取商户信息
        SysUserMerchant merchant = sysUserMerchantService.getById(merchantId);
        String countyId = merchant.getCountyId();
        // 根据省市县获取代理商信息
        SysUserAgent agent = sysUserAgentService.queryByCountyId(countyId);
        // 根据区县获取代理商的分润明细结果
        logger.info("agent is " + (agent == null ? null : agent.getId()));
        if (agent == null) {
            agent = new SysUserAgent();
            agent.setId("-1");
            agent.setBalanceMoney(new BigDecimal(0));
        }
        // 分润给代理商
        calcToAgent(memberOrder, agent, baseProfit);
        //  分润给群社合伙人
        calcToPartener(memberOrder, partenerProfit);
        // 修改为已分润
        memberOrderService.updateCalcStatus(memberOrder.getId(), 1);
    }

    private void calcToPartener(MemberOrder memberOrder, SysDictionaryParam baseProfit) {
        BigDecimal agentPrice;
        if (baseProfit == null) {
            return;
        }
        BigDecimal profit = new BigDecimal(baseProfit.getSysValue());
        agentPrice = profit.multiply(memberOrder.getMemberPrice()).divide(new BigDecimal(10000));
        //  更新群社代理人余额
        sysCommunityPartnerService.addBalance(memberOrder.getPartenerId(), agentPrice);

        MemberOrderCalcDetail memberOrderCalcDetail = new MemberOrderCalcDetail(memberOrder.getOutOrderNo(), memberOrder.getOrderType(), 4, memberOrder.getPartenerId(), agentPrice);
        memberOrderCalcDetail.setCountyId(memberOrder.getCountyId());
        memberOrderCalcDetail.setMemberPrice(memberOrder.getMemberPrice());
        // 插入分润记录
        memberOrderCalcDetailService.insert(memberOrderCalcDetail);
    }

    @Override
    @Transactional
    public void userMemberCalc(MemberOrder memberOrder, SysDictionaryParam baseProfit, SysDictionaryParam partenerProfit) {
        // 获取省市县码
        String countyId = memberOrder.getCountyId();
        // 根据省市县获取代理商信息
        SysUserAgent agent = sysUserAgentService.queryByCountyId(countyId);
        logger.info("agent is " + (agent == null ? null : agent.getId()));
        if (agent == null) {
            agent = new SysUserAgent();
            agent.setId("-1");
            agent.setBalanceMoney(new BigDecimal(0));
        }
        // 分润给代理商
        calcToAgent(memberOrder, agent, baseProfit);
        //  分润给群社合伙人
        calcToPartener(memberOrder, partenerProfit);
        // 修改为已分润
        memberOrderService.updateCalcStatus(memberOrder.getId(), 1);
    }


    @Override
    public void supplementProfit(String countyCode, BigDecimal userSupplement, BigDecimal merchantSupplement) {
        List<MemberOrderCalcDetail> userUnSupplements = memberOrderCalcDetailService.queryUnSupplement(countyCode, 1, 0);
        userUnSupplements.forEach(userUnSupplement -> {
            memberOrderCalcDetailService.supplement(userUnSupplement, userSupplement);
        });
        List<MemberOrderCalcDetail> merchantUnSupplements = memberOrderCalcDetailService.queryUnSupplement(countyCode, 1, 0);
        merchantUnSupplements.forEach(merchantUnSupplement -> {
            memberOrderCalcDetailService.supplement(merchantUnSupplement, merchantSupplement);
        });
    }

    /**
     * 分润给代理商
     *
     * @param memberOrder 待分润的订单
     * @param agent       代理商
     */
    private void calcToAgent(MemberOrder memberOrder, SysUserAgent agent, SysDictionaryParam baseProfit) {
        BigDecimal agentPrice;
        AreaAgentProfit areaAgentProfit = areaAgentProfitService.selectAreaAgentProfitByCountyId(memberOrder.getCountyId(), memberOrder.getOrderType());
        if (areaAgentProfit == null) {
            BigDecimal profitRatio = new BigDecimal(baseProfit.getSysValue());
            agentPrice = profitRatio.multiply(memberOrder.getMemberPrice()).divide(new BigDecimal(10000));
        } else {
            agentPrice = areaAgentProfit.getProfitRatio().multiply(memberOrder.getMemberPrice()).divide(new BigDecimal(10000));
        }
        agent.setBalanceMoney(agent.getBalanceMoney().add(agentPrice));
        sysUserAgentService.updateById(agent);
        MemberOrderCalcDetail memberOrderCalcDetail = new MemberOrderCalcDetail(memberOrder.getOutOrderNo(), memberOrder.getOrderType(), 2, agent.getId(), agentPrice);
        memberOrderCalcDetail.setCountyId(memberOrder.getCountyId());
        memberOrderCalcDetail.setMemberPrice(memberOrder.getMemberPrice());
        // 插入分润记录
        memberOrderCalcDetailService.insert(memberOrderCalcDetail);
    }
}
