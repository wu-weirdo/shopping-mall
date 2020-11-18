package com.edaochina.shopping.api.facade.trade.impl;

import com.edaochina.shopping.api.facade.trade.TradeDetailCalcFacade;
import com.edaochina.shopping.api.service.trade.TradeDetailCalcService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailCalcDTO;
import com.edaochina.shopping.domain.dto.trade.TradeDetailCalcListDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TradeDetailCalcFacadeImpl implements TradeDetailCalcFacade {

    @Resource
    TradeDetailCalcService service;

    /**
     * 获取交易明细列表
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult getCalcList(TradeDetailCalcListDTO dto) {
        return BaseResult.successResult(service.list(dto));
    }

    /**
     * 获取单条交易记录及交易分润明细
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult getCalcDetail(TradeDetailCalcDTO dto) {
        return BaseResult.successResult(service.getById(dto.getId()));
    }
}
