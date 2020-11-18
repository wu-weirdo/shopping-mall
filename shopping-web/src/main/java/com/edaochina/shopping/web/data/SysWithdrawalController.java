package com.edaochina.shopping.web.data;

import com.edaochina.shopping.api.facade.trade.WithdrawalRecordFacade;
import com.edaochina.shopping.common.annotation.OperationLogMark;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.SysWithdrawalRecordListDTO;
import com.edaochina.shopping.domain.dto.trade.WithdrawalRecordApprovalDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "后台提现管理")
@RestController
@RequestMapping("/sys/withdrawal")
public class SysWithdrawalController {

    @Resource
    WithdrawalRecordFacade facade;

    /**
     * 提现记录列表
     *
     * @param dto
     * @return
     */
    @ApiOperation("获取提现记录列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public AjaxResult list(@RequestBody SysWithdrawalRecordListDTO dto) {
        return facade.list(dto);
    }

    /**
     * 审批提现申请
     *
     * @param dto
     * @return
     */
    @ApiOperation("审批提现申请")
    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    @OperationLogMark("审批提现申请")
    public AjaxResult approval(@RequestBody WithdrawalRecordApprovalDTO dto) {
        return facade.approval(dto);
    }

}
