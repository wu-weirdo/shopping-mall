package com.edaochina.shopping.domain.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 24h
 */
@Data
public class InitiateTeamDTO implements Serializable {

    @NotBlank(message = "商品id不能为空")
    private String goodsId;

    private String communityId;

    @NotBlank(message = "用户id不能为空")
    private String userId;

    @NotBlank(message = "名字不能为空")
    private String userName;

    private String userAddress;

    @NotBlank(message = "电话不能为空")
    private String phone;

    @NotNull(message = "商品数量不能为空")
    private Integer goodsNum;

    private double totalPrice;

    private Date expectedDeliverTime;

    @ApiModelProperty("是否是app支付，0：小程序，1:app,默认为小程序")
    private Integer isAppPay;

    private String remark;

    /**
     * 分享商家id
     */
    private String shareMerchantId;
}
