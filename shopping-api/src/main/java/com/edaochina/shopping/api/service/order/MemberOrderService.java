package com.edaochina.shopping.api.service.order;

import com.edaochina.shopping.domain.entity.order.MemberOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author jintian
 * @date 2019/3/21 15:42
 */
public interface MemberOrderService {
    boolean updatePayStatusByOrderId(String orderId, Integer payStatus);

    List<MemberOrder> selectMemberOrderByOrderType(Integer orderType);

    boolean insertMemberOrder(MemberOrder memberOrder);

    boolean updateCalcStatus(int memberOrderId, int calcStatus);

    BigDecimal anticipatedAgentTodayInCome(String id, String userProfitRatio, String merchantProfitRatio);

    MemberOrder selectMemberOrderByOutOrderNo(String outOrderNo);

    int queryMermberNum(Date startTime, String agentId);

    BigDecimal anticipatedPartenerTodayInCome(String partenerId, String sysValue, String sysValue1);

    int queryPartenerMermberNum(Date beginDayOfMonth, String partenerId);
}
