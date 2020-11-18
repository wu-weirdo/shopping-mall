package com.edaochina.shopping.api.facade.profit;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailAgentDTO;
import com.edaochina.shopping.domain.dto.trade.TradeDetailPartenerDTO;

/**
 * @author jintian
 * @date 2019/4/2 10:37
 */
public interface MemberOrderProfitFacade {

    /**
     * 计算会员分润
     */
    void calcMemberInsertDetail();

    void areaProfitCalculate();

    AjaxResult dataStatis(TradeDetailAgentDTO dto);

    /**
     * 群社合伙人分润
     *
     * @param dto
     * @return
     */
    AjaxResult partenerDataStatis(TradeDetailPartenerDTO dto);
}
