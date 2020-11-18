package com.edaochina.shopping.domain.vo.trade;

import com.edaochina.shopping.domain.entity.trade.WithdrawalRecord;
import io.swagger.annotations.ApiModelProperty;

/**
 * 提现管理视图
 *
 * @author jintian
 * @date 2019/5/22 16:15
 */
public class WithdrawalRecordVo extends WithdrawalRecord {
    @ApiModelProperty("商家名称")
    private String shoperName;

    public String getShoperName() {
        return shoperName;
    }

    public void setShoperName(String shoperName) {
        this.shoperName = shoperName;
    }
}
