package com.edaochina.shopping.api.facade.trade.impl;

import com.edaochina.shopping.api.facade.trade.TradeDetailAgentFacade;
import com.edaochina.shopping.api.service.trade.TradeDetailAgentService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailAgentDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TradeDetailAgentFacadeImpl implements TradeDetailAgentFacade {

    @Resource
    TradeDetailAgentService tradeDetailAgentService;

    /**
     * 数据统计
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult dataStatis(TradeDetailAgentDTO dto) {
        return BaseResult.successResult(tradeDetailAgentService.dataStatis(dto));
    }
}
