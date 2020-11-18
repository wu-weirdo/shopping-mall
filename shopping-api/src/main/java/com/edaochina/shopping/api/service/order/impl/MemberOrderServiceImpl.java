package com.edaochina.shopping.api.service.order.impl;

import com.edaochina.shopping.api.dao.order.MemberOrderMapper;
import com.edaochina.shopping.api.service.order.MemberOrderService;
import com.edaochina.shopping.common.utils.date.LocalDateTimeUtil;
import com.edaochina.shopping.domain.entity.order.MemberOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 会员订单处理类
 *
 * @author jintian
 * @date 2019/3/21 15:43
 */
@Service
public class MemberOrderServiceImpl implements MemberOrderService {

    @Resource
    private MemberOrderMapper memberOrderMapper;

    @Override
    public boolean updatePayStatusByOrderId(String orderId, Integer payStatus) {
        return memberOrderMapper.updatePayStatusByOrderId(orderId, payStatus) > 0;
    }

    @Override
    public List<MemberOrder> selectMemberOrderByOrderType(Integer orderType) {
        return memberOrderMapper.selectMemberOrderByOrderType(orderType);
    }

    @Override
    public boolean insertMemberOrder(MemberOrder memberOrder) {
        return memberOrderMapper.insertMemberOrder(memberOrder) > 0;
    }

    @Override
    public boolean updateCalcStatus(int memberOrderId, int calcStatus) {
        return memberOrderMapper.updateCalcStatus(memberOrderId, calcStatus) > 0;
    }

    @Override
    public BigDecimal anticipatedAgentTodayInCome(String id, String userProfitRatio, String merchantProfitRatio) {

        return memberOrderMapper.anticipatedAgentTodayInCome(id, userProfitRatio, merchantProfitRatio, LocalDateTimeUtil.getStartTimeOfToday());
    }

    @Override
    public MemberOrder selectMemberOrderByOutOrderNo(String outOrderNo) {
        return memberOrderMapper.selectMemberOrderByOutOrderNo(outOrderNo);
    }

    @Override
    public int queryMermberNum(Date startTime, String agentId) {
        return memberOrderMapper.queryMermberNum(startTime, agentId);
    }

    @Override
    public BigDecimal anticipatedPartenerTodayInCome(String partenerId, String userProfitRatio, String merchantProfitRatio) {
        return memberOrderMapper.anticipatedPartenerTodayInCome(partenerId, userProfitRatio, merchantProfitRatio, LocalDateTimeUtil.getStartTimeOfToday());
    }

    @Override
    public int queryPartenerMermberNum(Date beginDayOfMonth, String partenerId) {
        return memberOrderMapper.queryPartenerMermberNum(beginDayOfMonth, partenerId);
    }
}
