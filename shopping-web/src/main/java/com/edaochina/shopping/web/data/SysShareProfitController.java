package com.edaochina.shopping.web.data;

import com.edaochina.shopping.api.service.trade.TradeDetailShareProfitService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.trade.SysShareGoodsProfitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 推广分润明细修改
 *
 * @author jintian
 * @date 2019/11/5 15:46
 */
@RestController
@RequestMapping("sys/shareProfit")
public class SysShareProfitController {
    @Autowired
    private TradeDetailShareProfitService tradeDetailShareProfitService;

    @RequestMapping("getShareProfitList")
    public AjaxResult getShareProfitList(@RequestBody SysShareGoodsProfitDTO dto) {
        return BaseResult.successResult(tradeDetailShareProfitService.getShareProfitList(dto));
    }
}
