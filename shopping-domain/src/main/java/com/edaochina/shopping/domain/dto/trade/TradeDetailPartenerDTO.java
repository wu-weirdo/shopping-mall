package com.edaochina.shopping.domain.dto.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author jintian
 * @date 2019/7/29 10:46
 */
@ApiModel("群社代理商利润查询条件")
public class TradeDetailPartenerDTO {
    @ApiModelProperty("群社代理商id")
    private String partenerId;

    @ApiModelProperty("查询的月份")
    private String month;

    public String getPartenerId() {
        return partenerId;
    }

    public void setPartenerId(String partenerId) {
        this.partenerId = partenerId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
