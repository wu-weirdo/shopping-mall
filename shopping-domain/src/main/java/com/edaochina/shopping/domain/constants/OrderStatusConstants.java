package com.edaochina.shopping.domain.constants;

public interface OrderStatusConstants {

    Integer WAIT_SHARE = 10;

    Integer WAIT_USE = 20;

    Integer COLLECT_WAIT_USE = 21;

    Integer WAIT_EVALUATE = 30;

    Integer REFUNDED = 40;

    Integer EVALUATED = 50;

    Integer OUT_TIME = 60;

    /**
     * 等待微信回调
     */
    Integer WAIT_CALLBACK = 90;

    Integer COLLECT_WAIT_CALLBACK = 91;

    Integer WAIT_PAY = 70;

    Integer PAY_FAIL = 80;

}
