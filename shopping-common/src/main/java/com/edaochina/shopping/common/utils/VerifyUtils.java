package com.edaochina.shopping.common.utils;


import java.util.regex.Pattern;

public class VerifyUtils {

    /**
     * 正则表达式:验证身份证
     */
    public static final String REGEX_IDENTITY_NO = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

    public static final String REGEX_PHONE = "^\\d{11}$";

    /**
     * 校验身份证
     *
     * @param identityNo
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIdentityNo(String identityNo) {
        return Pattern.matches(REGEX_IDENTITY_NO, identityNo);
    }

    /**
     * 校验手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        return Pattern.matches(REGEX_PHONE, phone);
    }


    public static void main(String[] args) {
        System.out.println(isIdentityNo("342921199303011615"));
        System.out.println(isPhone("17717037005"));
    }

}
