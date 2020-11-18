package com.edaochina.shopping.domain.constants;

/**
 * 角色類型
 *
 * @author jintian
 * @date 2019/5/31 14:14
 */
public interface RoleConstants {
    /**
     * 管理员
     */
    String ADMIN_ROLE_STRING = "1";
    /**
     * 商户
     */
    String MERCHANT_ROLE_STRING = "3";
    /**
     * 代理商
     */
    String AGENT_ROLE_STRING = "4";
    /**
     * 普通用户
     */
    String USER_ROLE_STRING = "5";
    /**
     * 群社合伙人
     */
    String COMMUNITY_PARTENER_STRING = "6";


    int ADMIN_ROLE = 1;

    int MERCHANT_ROLE = 3;


    int AGENT_ROLE = 4;

    int USER_ROLE = 5;

    int COMMUNITY_PARTENER = 6;

}
