package com.edaochina.shopping.domain.dto.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel("申请提现请求")
@Data
public class WithdrawalApplyDTO implements Serializable {

    /**
     * 用户
     */
    @ApiModelProperty("申请提现用户id")
    private String userId;

    @ApiModelProperty("申请提现用户姓名")
    private String userName;

    @ApiModelProperty("申请提现联系人号码")
    private String phone;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("角色类型,3:商家，4：代理商，6：群社合伙人")
    private String userRole;

    /**
     * 开户银行
     */
    @ApiModelProperty("开户行名称")
    private String bankName;

    /**
     * 银行卡号码
     */
    @ApiModelProperty("银行卡号")
    private String bankNumber;

    @ApiModelProperty("银行编码")
    private String bankCode;

    /**
     * 申请提现金额
     */
    @ApiModelProperty("申请提现金额")
    private BigDecimal applyMoney;

    /**
     * 提现类型
     */
    @ApiModelProperty("提现类型，1：银行卡，2：微信")
    private Integer withdrawalType = 1;

    /**
     * 提现到零钱的openId
     */
    @ApiModelProperty("提现到微信时微信号在小程序中的openId")
    private String openId;
}
