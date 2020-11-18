package com.edaochina.shopping.domain.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车支付请求
 *
 * @author jintian
 * @date 2019/4/18 10:47
 */
@Data
public class ShoppingPayDTO {
    @NotBlank
    private String userId;
    @NotBlank
    private String openId;

    private List<ShoppingGoods> selectedGoods;

    private BigDecimal totalPrice;

    @ApiModelProperty("是否是app支付，0：小程序，1:app,默认为小程序")
    private Integer isAppPay;

    public Integer getIsAppPay() {
        return isAppPay;
    }

    public void setIsAppPay(Integer isAppPay) {
        this.isAppPay = isAppPay;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public List<ShoppingGoods> getSelectedGoods() {
        return selectedGoods;
    }

    public void setSelectedGoods(List<ShoppingGoods> selectedGoods) {
        this.selectedGoods = selectedGoods;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
