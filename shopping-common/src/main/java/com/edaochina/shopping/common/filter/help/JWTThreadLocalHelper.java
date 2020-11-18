package com.edaochina.shopping.common.filter.help;

import com.edaochina.shopping.domain.base.jwt.JWTBean;

/**
 * @author jintian
 * @date 2019/9/11 14:35
 */
public class JWTThreadLocalHelper {

    private static final ThreadLocal<JWTBean> THREAD_LOCAL = new ThreadLocal<>();

    public static JWTBean get() {
        return THREAD_LOCAL.get();
    }

    public static void put(JWTBean jwtBean) {
        THREAD_LOCAL.set(jwtBean);
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }

    public static String logRoleAndUser() {
        JWTBean jwtBean = get();
        if (jwtBean != null) {
            return " , role :" + jwtBean.getRole() + " ,userId :" + jwtBean.getId();
        } else {
            return "";
        }
    }
}
