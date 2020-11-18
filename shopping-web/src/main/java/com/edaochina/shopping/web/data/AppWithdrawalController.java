package com.edaochina.shopping.web.data;

import com.edaochina.shopping.api.facade.trade.WithdrawalRecordFacade;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.trade.AppWithdrawalRecordListDTO;
import com.edaochina.shopping.domain.dto.trade.LastWithdrawalDTO;
import com.edaochina.shopping.domain.dto.trade.WithdrawalApplyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "小程序提现")
@RestController
@RequestMapping("/app/withdrawal")
public class AppWithdrawalController {

    @Resource
    WithdrawalRecordFacade facade;

    /**
     * app上发起提现申请
     *
     * @param dto
     * @return
     */
    @ApiOperation("申请提现")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public AjaxResult apply(@RequestBody WithdrawalApplyDTO dto) {
        return facade.apply(dto);
    }

    /**
     * app上查询当前登录用户的提现申请记录
     *
     * @param dto
     * @return
     */
    @ApiOperation("小程序查询提现记录")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public AjaxResult selectApplyRecord(@RequestBody AppWithdrawalRecordListDTO dto) {
        return facade.selectApplyRecord(dto);
    }

    @ApiOperation("最近一次提现到银行卡记录")
    @RequestMapping(value = "/lastWithdrawalToBankInfo", method = RequestMethod.POST)
    public AjaxResult lastWithdrawalToBankInfo(@RequestBody LastWithdrawalDTO lastWithdrawalDTO) {
        return BaseResult.successResult(facade.getLastWithdrawalToBankInfo(lastWithdrawalDTO));
    }
}
