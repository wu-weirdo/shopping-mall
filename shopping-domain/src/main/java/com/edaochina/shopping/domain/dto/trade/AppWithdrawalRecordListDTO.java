package com.edaochina.shopping.domain.dto.trade;

import com.edaochina.shopping.domain.base.page.Pages;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("小程序提现记录查询请求")
public class AppWithdrawalRecordListDTO implements Serializable {
    /**
     * 用户
     */
    @ApiModelProperty("提现申请人")
    private String userId;

    @ApiModelProperty("角色，3：商家，4：代理商，6群社合伙人")
    private String userRole;

    /**
     * 分页数据
     */
    private Pages pages;

}
