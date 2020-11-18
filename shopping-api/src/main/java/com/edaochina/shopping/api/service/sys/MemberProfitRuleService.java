package com.edaochina.shopping.api.service.sys;

import com.edaochina.shopping.domain.entity.order.MemberOrder;
import com.edaochina.shopping.domain.entity.sys.SysDictionaryParam;

import java.math.BigDecimal;

/**
 * @author jintian
 * @date 2019/3/22 11:17
 */
public interface MemberProfitRuleService {

    /**
     * 商家会员费用分润
     *
     * @param memberOrder
     */
    void merchantMemberCalc(MemberOrder memberOrder, SysDictionaryParam baseProfit, SysDictionaryParam partenerProfit);

    /**
     * 普通用户会员费用分润
     *
     * @param memberOrder
     */
    void userMemberCalc(MemberOrder memberOrder, SysDictionaryParam baseProfit, SysDictionaryParam partenerProfit);


    void supplementProfit(String countyCode, BigDecimal userSupplement, BigDecimal merchantSupplement);
}
