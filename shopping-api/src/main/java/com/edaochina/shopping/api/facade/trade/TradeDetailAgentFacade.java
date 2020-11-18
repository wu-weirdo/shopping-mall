package com.edaochina.shopping.api.facade.trade;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailAgentDTO;

public interface TradeDetailAgentFacade {

    /**
     * 数据统计
     *
     * @param dto
     * @return
     */
    AjaxResult dataStatis(TradeDetailAgentDTO dto);

}
