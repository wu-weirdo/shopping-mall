package com.edaochina.shopping.domain.constants;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 分润静态值
 * @since : 2019-07-25
 */
public interface ProfitConstants {

    /**
     * 分润用户类型
     */
    interface MemberType {

        /**
         * 商家
         */
        int MERCHANT = 1;

        /**
         * 代理
         */
        int AGENT = 2;

        /**
         * 平台
         */
        int ADMIN = 3;

        /**
         * 社区合伙人
         */
        int COMMUNITY_PARTNER = 4;

    }

    /**
     * 订单类型
     */
    interface OrderType {

        /**
         * 用户
         */
        int USER = 1;

        /**
         * 商家
         */
        int MERCHANT = 2;

    }

}
