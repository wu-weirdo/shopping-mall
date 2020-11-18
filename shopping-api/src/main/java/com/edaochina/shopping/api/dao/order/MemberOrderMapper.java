package com.edaochina.shopping.api.dao.order;

import com.edaochina.shopping.domain.entity.order.MemberOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 会员订单表
 *
 * @author jintian
 * @date 2019/3/21 15:34
 */
public interface MemberOrderMapper {


    int updatePayStatusByOrderId(@Param("orderId") String orderId, @Param("payStatus") Integer payStatus);

    List<MemberOrder> selectMemberOrderByOrderType(@Param("orderType") Integer orderType);

    int insertMemberOrder(MemberOrder memberOrder);

    int updateCalcStatus(@Param("id") int memberOrderId, @Param("calcStatus") int calcStatus);

    BigDecimal anticipatedAgentTodayInCome(@Param("id") String id, @Param("userProfitRatio") String userProfitRatio,
                                           @Param("merchantProfitRatio") String merchantProfitRatio, @Param("startTime") LocalDateTime startTIme);

    MemberOrder selectMemberOrderByOutOrderNo(String outOrderNo);

    int queryMermberNum(@Param("startTime") Date startTime, @Param("agentId") String agentId);

    BigDecimal anticipatedPartenerTodayInCome(@Param("partenerId") String partenerId, @Param("userProfitRatio") String userProfitRatio, @Param("merchantProfitRatio") String merchantProfitRatio, @Param("startTime") LocalDateTime startTime);

    int queryPartenerMermberNum(@Param("beginTime") Date beginTime, @Param("partenerId") String partenerId);
}
