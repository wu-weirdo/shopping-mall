package com.edaochina.shopping.web.data;

import com.edaochina.shopping.api.facade.trade.TradeDetailCalcFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailCalcDTO;
import com.edaochina.shopping.domain.dto.trade.TradeDetailCalcListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("交易记录")
@RestController
@RequestMapping("/sys/trade")
public class SysTradeCalcController {

    @Resource
    TradeDetailCalcFacade facade;

    /**
     * 查询交易列表
     *
     * @param dto
     * @return
     */
    @ApiOperation("获取交易记录列表")
    @RequestMapping(value = "/calc/list", method = RequestMethod.POST)
    public AjaxResult getCalcList(@RequestBody TradeDetailCalcListDTO dto) {
        return facade.getCalcList(dto);
    }

    /**
     * 获取单条交易记录及交易分润明细
     *
     * @param dto
     * @return
     */
    @ApiOperation("获取单条交易记录明细")
    @RequestMapping(value = "/calc/detail", method = RequestMethod.POST)
    public AjaxResult getCalcDetail(@RequestBody TradeDetailCalcDTO dto) {
        return facade.getCalcDetail(dto);
    }
}
