package com.edaochina.shopping.api.facade.trade;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailCalcDTO;
import com.edaochina.shopping.domain.dto.trade.TradeDetailCalcListDTO;

public interface TradeDetailCalcFacade {

    /**
     * 获取交易明细列表
     *
     * @param dto
     * @return
     */
    AjaxResult getCalcList(TradeDetailCalcListDTO dto);

    AjaxResult getCalcDetail(TradeDetailCalcDTO dto);
}
