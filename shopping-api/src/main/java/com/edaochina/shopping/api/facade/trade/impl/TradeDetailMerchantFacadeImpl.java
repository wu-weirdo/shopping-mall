package com.edaochina.shopping.api.facade.trade.impl;

import com.edaochina.shopping.api.facade.trade.TradeDetailMerchantFacade;
import com.edaochina.shopping.api.service.trade.TradeDetailMerchantService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailMerchantDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TradeDetailMerchantFacadeImpl implements TradeDetailMerchantFacade {

    @Resource
    TradeDetailMerchantService tradeDetailMerchantService;

    /**
     * 数据统计
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult dataStatis(TradeDetailMerchantDTO dto) {
        return BaseResult.successResult(tradeDetailMerchantService.dataStatis(dto));
    }

}
