package com.edaochina.shopping.api.dao.trade;

import com.edaochina.shopping.domain.dto.order.MemberOrderCountDTO;
import com.edaochina.shopping.domain.entity.trade.MemberOrderCalcDetail;
import com.edaochina.shopping.domain.vo.order.SysMemberOrderCountVO;
import com.edaochina.shopping.domain.vo.order.SysMemberOrderMonthVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MemberOrderCalcDetailMapper {

    List<MemberOrderCalcDetail> selectMemberOrderCalcDetailByProfitType(@Param("profitType") Integer profitType);

    int insert(MemberOrderCalcDetail memberOrderCalcDetail);

    List<MemberOrderCalcDetail> queryUnSupplement(@Param("countyCode") String countyCode, @Param("memberType") Integer memberType, @Param("supplementType") Integer supplementType);

    int setToSupplemented(@Param("id") Long id);

    List<SysMemberOrderCountVO> queryUserMemberOrderCount(MemberOrderCountDTO memberOrderCountDto);

    /**
     * 查询月度分润
     *
     * @param memberOrderCountDto 参数
     * @return 视图类
     */
    List<SysMemberOrderMonthVO> selectProfitGroupByMonth(MemberOrderCountDTO memberOrderCountDto);

    BigDecimal getAgentCalcCountInfo(@Param("agentId") String agentId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    BigDecimal getPartenerCalcCountInfo(@Param("partenerId") String partenerId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
