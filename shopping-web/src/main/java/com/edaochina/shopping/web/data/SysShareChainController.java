package com.edaochina.shopping.web.data;

import com.edaochina.shopping.api.service.trade.TradeDetailChainProfitService;
import com.edaochina.shopping.api.service.trade.TradeDetailShareProfitService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.trade.SysChainGoodsProfitDTO;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 渠道分润明细
 *
 * @author jintian
 * @date 2019/11/5 15:46
 */
@RestController
@RequestMapping("sys/chainProfit")
public class SysShareChainController {
    @Autowired
    private TradeDetailChainProfitService tradeDetailChainProfitService;

    @RequestMapping("getChainProfitList")
    public AjaxResult getChainProfitList(@RequestBody SysChainGoodsProfitDTO dto) {
        return BaseResult.successResult(tradeDetailChainProfitService.getChainProfitList(dto));
    }
}
