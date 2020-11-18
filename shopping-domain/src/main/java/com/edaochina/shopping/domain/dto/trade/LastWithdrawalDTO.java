package com.edaochina.shopping.domain.dto.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author jintian
 * @date 2019/7/26 11:54
 */
@ApiModel("最近一次提现到银行卡记录查询")
public class LastWithdrawalDTO {

    @ApiModelProperty("用户角色类型，3：商家，4：代理商，6：群社代理商")
    private String userRole;

    @ApiModelProperty("用户id")
    private String userId;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
