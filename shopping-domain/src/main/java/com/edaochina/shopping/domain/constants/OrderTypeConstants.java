package com.edaochina.shopping.domain.constants;


/**
 * 支付的订单类型
 *
 * @author jintian
 * @date 2019/4/18 11:58
 */
public enum OrderTypeConstants {

    SECKILL_ORDER(1, "秒杀订单"),
    MERCHANT_MEMBER_ORDER(2, "商家会员订单"),
    SHOPING_CART_ORDER(3, "购物车订单"),
    USER_MEMBER_ORDER(4, "用户会员订单");

    private Integer code;

    private String scribe;

    OrderTypeConstants(Integer code, String scribe) {
        this.code = code;
        this.scribe = scribe;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getScribe() {
        return scribe;
    }

    public void setScribe(String scribe) {
        this.scribe = scribe;
    }
}
