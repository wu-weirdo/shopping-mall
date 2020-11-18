package com.edaochina.shopping.web.data;

import com.edaochina.shopping.api.facade.profit.MemberOrderProfitFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.TradeDetailAgentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 代理商数据统计
 *
 * @author fish
 */
@Api(description = "代理商数据统计")
@RestController
@RequestMapping("/app/data/agent")
public class AppAgentDataController {

    @Resource
    private MemberOrderProfitFacade memberOrderProfitFacade;

    @ApiOperation("代理商盈利数据统计")
    @RequestMapping(value = "/dataStatis", method = RequestMethod.POST)
    public AjaxResult dataStatis(@RequestBody TradeDetailAgentDTO dto) {
        return memberOrderProfitFacade.dataStatis(dto);
    }
}
