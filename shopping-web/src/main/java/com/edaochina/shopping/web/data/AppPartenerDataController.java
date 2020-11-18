package com.edaochina.shopping.web.data;

import com.edaochina.shopping.api.facade.profit.MemberOrderProfitFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailPartenerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jintian
 * @date 2019/7/29 10:44
 */
@Api(description = "群社代理商利润")
@RestController
@RequestMapping("/app/data/partener")
public class AppPartenerDataController {

    @Resource
    private MemberOrderProfitFacade memberOrderProfitFacade;

    @ApiOperation("群社代理商分润查看")
    @RequestMapping(value = "/dataStatis", method = RequestMethod.POST)
    public AjaxResult dataStatis(@RequestBody TradeDetailPartenerDTO dto) {
        return memberOrderProfitFacade.partenerDataStatis(dto);
    }
}
