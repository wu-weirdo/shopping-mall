package com.edaochina.shopping.api.facade.profit.impl;

import com.edaochina.shopping.api.dao.sys.SysDictionaryParamMapper;
import com.edaochina.shopping.api.dao.trade.TradeDetailChainProfitMapper;
import com.edaochina.shopping.api.dao.trade.TradeDetailShareProfitMapper;
import com.edaochina.shopping.api.facade.profit.ProfitFacade;
import com.edaochina.shopping.api.service.order.SysOrderService;
import com.edaochina.shopping.api.service.trade.TradeDetailMerchantService;
import com.edaochina.shopping.api.service.user.SysUserAgentService;
import com.edaochina.shopping.api.service.user.SysUserMerchantService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.domain.constants.GoodsTypeConstants;
import com.edaochina.shopping.domain.constants.RoleConstants;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;
import com.edaochina.shopping.domain.entity.trade.TradeDetailChainProfit;
import com.edaochina.shopping.domain.entity.trade.TradeDetailMerchant;
import com.edaochina.shopping.domain.entity.trade.TradeDetailShareProfit;
import com.edaochina.shopping.domain.entity.user.SysUserAgent;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProfitFacadeImpl implements ProfitFacade {

    private Logger logger = LoggerFactory.getLogger(ProfitFacadeImpl.class);

    @Resource
    TradeDetailMerchantService tradeDetailMerchantService;
    @Resource
    SysOrderService sysOrderService;
    @Resource
    SysUserMerchantService sysUserMerchantService;
    @Autowired
    private SysDictionaryParamMapper sysDictionaryParamMapper;
    @Resource
    private TradeDetailShareProfitMapper tradeDetailShareProfitMapper;
    @Resource
    private TradeDetailChainProfitMapper chainProfitMapper;
    @Resource
    private SysUserAgentService sysUserAgentService;

    private volatile boolean CALA_REUNING = false;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void calcProfitAndInsertDetail() {
        // 保证每次只有一个运行着，防止多条运行造成多次分润
        if (CALA_REUNING) {
            return;
        }
        CALA_REUNING = true;
        try {
            // 查询分享分润比例
            // 商家分享分润比例
            SysDictionaryParam merchantRate = sysDictionaryParamMapper.querySysValue("share_goods", "merchant_profit_rate");
            BigDecimal merchantProfitRate = new BigDecimal(merchantRate.getSysValue()).divide(new BigDecimal(10000));
            // 代理商分润比例
            SysDictionaryParam agentRate = sysDictionaryParamMapper.querySysValue("share_goods", "agent_profit_rate");
            BigDecimal agentProfitRate = new BigDecimal(agentRate.getSysValue()).divide(new BigDecimal(10000));
            // 供应链商品分润比例
            SysDictionaryParam chainMerchantRate = sysDictionaryParamMapper.querySysValue("supply_chain", "merchant_profit_rate");
            BigDecimal chainMerchantProfitRate = new BigDecimal(chainMerchantRate.getSysValue()).divide(new BigDecimal(10000));
            // 代理商分润比例
            SysDictionaryParam chainAgentRate = sysDictionaryParamMapper.querySysValue("supply_chain", "agent_profit_rate");
            BigDecimal chainAgentProfitRate = new BigDecimal(chainAgentRate.getSysValue()).divide(new BigDecimal(10000));


            int num = 0;
            // 每次一百条
            do {
                List<OrderMain> orderMains = sysOrderService.queryHasNotCalcOrder();
                for (OrderMain orderMain : orderMains) {
                    // 现逻辑直接给予全部的金额给商户
                    orderProfit(orderMain, merchantProfitRate, agentProfitRate, chainMerchantProfitRate, chainAgentProfitRate);
                    // 更新成已分润
                    orderMain.setCalcStatus(1);
                    sysOrderService.updateById(orderMain);
                }
                num = orderMains.size();
            } while (num == 100);
        } finally {
            CALA_REUNING = false;
        }
    }


    /**
     * 订单分润
     * TODO 代码重构
     * @param orderMain
     */
    @Transactional(rollbackFor = Exception.class)
    public void orderProfit(OrderMain orderMain, BigDecimal merchantProfitRate, BigDecimal agentProfitRate, BigDecimal chainMerchantProfitRate, BigDecimal chainAgentProfitRate) {
        TradeDetailMerchant merchant = new TradeDetailMerchant();
        merchant.setId(IdUtils.nextId());
        merchant.setOutTradeNo(orderMain.getId());
        merchant.setPayObject(orderMain.getGoodsName());
        merchant.setCostPrice(orderMain.getCostPrice());
        // 分享费率大于0切有分享商家时候
        // 分享商品分润
        if (StringUtils.isNotBlank(orderMain.getShareMerchantId()) && orderMain.getShareRate().compareTo(new BigDecimal(0)) > 0) {
            // 获取分润金额
            BigDecimal shareFee = orderMain.getTotalPrice().multiply(orderMain.getShareRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
            // 设置商家实际获取的金额
            merchant.setTotalIncome(orderMain.getTotalPrice().subtract(shareFee));
            //  添加商户分享分润记录
            TradeDetailShareProfit merchantShareProfit = new TradeDetailShareProfit();
            merchantShareProfit.setShareProfit(shareFee);
            merchantShareProfit.setOrderId(orderMain.getId());
            merchantShareProfit.setUserId(orderMain.getShareMerchantId());
            merchantShareProfit.setUserType(RoleConstants.MERCHANT_ROLE);
            merchantShareProfit.setToAccountMoney(shareFee.multiply(merchantProfitRate).setScale(2, BigDecimal.ROUND_HALF_UP));

            tradeDetailShareProfitMapper.insertSelective(merchantShareProfit);
            sysUserMerchantService.addProfitMoney(merchantShareProfit.getUserId(), merchantShareProfit.getToAccountMoney());


            //  添加代理商分享分润记录
            TradeDetailShareProfit agentShareProfit = new TradeDetailShareProfit();
            agentShareProfit.setShareProfit(shareFee);
            agentShareProfit.setOrderId(orderMain.getId());
            // 获取代理商id
            SysUserAgent agent = sysUserAgentService.queryMerchantAffiAgent(orderMain.getShareMerchantId());
            agentShareProfit.setUserId(agent == null ? "" : agent.getId());

            agentShareProfit.setUserType(RoleConstants.AGENT_ROLE);
            agentShareProfit.setToAccountMoney(shareFee.multiply(agentProfitRate).setScale(2, BigDecimal.ROUND_HALF_UP));
            tradeDetailShareProfitMapper.insertSelective(agentShareProfit);
            sysUserAgentService.addProfitMoney(agentShareProfit.getUserId(), agentShareProfit.getToAccountMoney());

            //  添加系统分享分润记录
            TradeDetailShareProfit sysShareProfit = new TradeDetailShareProfit();
            sysShareProfit.setShareProfit(shareFee);
            sysShareProfit.setOrderId(orderMain.getId());
            sysShareProfit.setUserId("");
            sysShareProfit.setUserType(RoleConstants.ADMIN_ROLE);
            sysShareProfit.setToAccountMoney(shareFee
                    .subtract(agentShareProfit.getToAccountMoney())
                    .subtract(merchantShareProfit.getToAccountMoney())
                    .setScale(2, BigDecimal.ROUND_HALF_UP));
            tradeDetailShareProfitMapper.insertSelective(sysShareProfit);
            // 供应链分润 10%的部分
        } else if (ObjectUtils.equals(orderMain.getGoodsType(), GoodsTypeConstants.CHAIN_COLLECT_GOODS)) {
            // 获取分润金额
            BigDecimal chainFee = orderMain.getActualPrice().subtract(orderMain.getCostPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);

            //  添加商户分享分润记录
            TradeDetailChainProfit merchantShareProfit = new TradeDetailChainProfit();
            merchantShareProfit.setChainProfit(chainFee);
            merchantShareProfit.setOrderId(orderMain.getId());
            merchantShareProfit.setUserId(orderMain.getShoperId());
            merchantShareProfit.setUserType(RoleConstants.MERCHANT_ROLE);
            merchantShareProfit.setToAccountMoney(chainFee.multiply(chainMerchantProfitRate).setScale(2, BigDecimal.ROUND_HALF_UP));

            chainProfitMapper.insert(merchantShareProfit);
            sysUserMerchantService.addProfitMoney(merchantShareProfit.getUserId(), merchantShareProfit.getToAccountMoney());


            //  添加代理商分享分润记录
            TradeDetailChainProfit agentShareProfit = new TradeDetailChainProfit();
            agentShareProfit.setChainProfit(chainFee);
            agentShareProfit.setOrderId(orderMain.getId());
            // 获取代理商id
            SysUserAgent agent = sysUserAgentService.queryMerchantAffiAgent(orderMain.getShoperId());
            agentShareProfit.setUserId(agent == null ? "" : agent.getId());

            agentShareProfit.setUserType(RoleConstants.AGENT_ROLE);
            agentShareProfit.setToAccountMoney(chainFee.multiply(chainAgentProfitRate).setScale(2, BigDecimal.ROUND_HALF_UP));
            chainProfitMapper.insert(agentShareProfit);
            sysUserAgentService.addProfitMoney(agentShareProfit.getUserId(), agentShareProfit.getToAccountMoney());

            //  添加系统分享分润记录
            TradeDetailChainProfit sysShareProfit = new TradeDetailChainProfit();
            sysShareProfit.setChainProfit(chainFee);
            sysShareProfit.setOrderId(orderMain.getId());
            sysShareProfit.setUserId("");
            sysShareProfit.setUserType(RoleConstants.ADMIN_ROLE);
            sysShareProfit.setToAccountMoney(chainFee
                    .subtract(agentShareProfit.getToAccountMoney())
                    .subtract(merchantShareProfit.getToAccountMoney())
                    .setScale(2, BigDecimal.ROUND_HALF_UP));
            chainProfitMapper.insert(sysShareProfit);

            // 成本价给到商户
            merchant.setTotalIncome(orderMain.getCostPrice());
        } else {
            merchant.setTotalIncome(orderMain.getTotalPrice());
        }
        merchant.setMerchantId(orderMain.getShoperId());
        merchant.setMerchantName(orderMain.getShoperName());
        merchant.setPayUserId(orderMain.getUserId());
        merchant.setPayUserName(orderMain.getUserName());
        merchant.setPayTime(orderMain.getCreateTime());
        merchant.setCreateTime(LocalDateTime.now());
        merchant.setRemark("商家分润");
        // 修改核销时间
        merchant.setWriteOffTime(orderMain.getWriteOffTime());
        tradeDetailMerchantService.save(merchant);
        // 商家加上剩余金额加上分润金额
        int i = sysUserMerchantService.addProfitMoney(orderMain.getShoperId(), merchant.getTotalIncome());
        if (i <= 0) {
            throw new YidaoException(ReturnData.BALANCE_MONEY_NOT_ENOUGH);
        }
    }
}
