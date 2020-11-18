package com.edaochina.shopping.api.service.trade;

import com.edaochina.shopping.domain.dto.order.MemberOrderCountDTO;
import com.edaochina.shopping.domain.entity.trade.MemberOrderCalcDetail;
import com.edaochina.shopping.domain.vo.order.SysMemberOrderCountVO;
import com.edaochina.shopping.domain.vo.order.SysMemberOrderMonthVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MemberOrderCalcDetailService {

    List<MemberOrderCalcDetail> selectMemberOrderCalcDetailByProfitType(Integer profitType);

    int insert(MemberOrderCalcDetail memberOrderCalcDetail);

    List<SysMemberOrderCountVO> queryUserMemberOrderCount(MemberOrderCountDTO memberOrderCountDto);

    List<MemberOrderCalcDetail> queryUnSupplement(String countyCode, Integer memberType, Integer supplementType);

    void supplement(MemberOrderCalcDetail userUnSupplement, BigDecimal ratio);

    BigDecimal getAgentCalcCountInfo(String agentId, Date startTime, Date endTime);

    /**
     * 查询月度分润
     *
     * @param memberOrderCountDto 参数
     * @return 视图类
     */
    List<SysMemberOrderMonthVO.MonthDetail> month(MemberOrderCountDTO memberOrderCountDto);

    String export(MemberOrderCountDTO dto);

    BigDecimal getPartenerCalcCountInfo(String partenerId, Date beginDayOfWeek, Date endDayOfWeek);
}
