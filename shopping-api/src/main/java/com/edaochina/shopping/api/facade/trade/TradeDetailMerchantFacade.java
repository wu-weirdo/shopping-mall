package com.edaochina.shopping.api.facade.trade;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailMerchantDTO;

public interface TradeDetailMerchantFacade {

    /**
     * 数据统计
     *
     * @param dto
     * @return
     */
    AjaxResult dataStatis(TradeDetailMerchantDTO dto);

}
