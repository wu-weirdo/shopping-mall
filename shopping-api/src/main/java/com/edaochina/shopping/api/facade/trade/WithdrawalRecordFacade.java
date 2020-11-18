package com.edaochina.shopping.api.facade.trade;

import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.domain.dto.trade.*;
import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;

public interface WithdrawalRecordFacade {

    /**
     * 查询所有订单
     *
     * @param dto
     * @return
     */
    AjaxResult list(SysWithdrawalRecordListDTO dto);

    /**
     * 审批订单
     *
     * @param dto
     * @return
     */
    AjaxResult approval(WithdrawalRecordApprovalDTO dto);

    /**
     * app上发起提现申请
     *
     * @param dto
     * @return
     */
    AjaxResult apply(WithdrawalApplyDTO dto);

    /**
     * app上查询当前用户发起的提现记录
     *
     * @param dto
     * @return
     */
    AjaxResult selectApplyRecord(AppWithdrawalRecordListDTO dto);

    WithdrawalRecord getLastWithdrawalToBankInfo(LastWithdrawalDTO lastWithdrawalDTO);

    void withdrawalStatusHandler();

}
