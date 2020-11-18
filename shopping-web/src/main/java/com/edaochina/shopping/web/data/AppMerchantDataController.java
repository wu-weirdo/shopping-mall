package com.edaochina.shopping.web.data;

import com.edaochina.shopping.api.facade.trade.TradeDetailMerchantFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailMerchantDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 商家数据统计
 *
 * @author fish
 */
@Api(description = "商家盈利数据统计")
@RestController
@RequestMapping("/app/data/merchant")
public class AppMerchantDataController {

    @Resource
    private TradeDetailMerchantFacade facade;

    @ApiOperation("商家盈利数据查看")
    @RequestMapping(value = "/dataStatis", method = RequestMethod.POST)
    public AjaxResult dataStatis(@RequestBody TradeDetailMerchantDTO dto) {
        return facade.dataStatis(dto);
    }
}
