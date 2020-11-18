package com.edaochina.shopping.domain.dto.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("提现申请审批")
public class WithdrawalRecordApprovalDTO implements Serializable {
    @ApiModelProperty("提现申请记录id")
    private String id;


    @ApiModelProperty("审批状态结论-1：不通过，1：通过")
    private Integer status;

    /**
     * 审批拒绝理由
     */
    @ApiModelProperty("审批不通过原因")
    private String refuseReason;

}
