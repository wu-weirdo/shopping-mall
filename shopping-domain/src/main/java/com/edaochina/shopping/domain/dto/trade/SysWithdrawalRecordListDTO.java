package com.edaochina.shopping.domain.dto.trade;

import com.edaochina.shopping.domain.base.page.Pages;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysWithdrawalRecordListDTO implements Serializable {

    /**
     * 提现类型
     */
    @ApiModelProperty("提现类型,1:提现到银行卡，2：提现到零钱")
    private Integer withdrawalType = 1;


    /**
     * 分页数据
     */
    private Pages pages;

}
