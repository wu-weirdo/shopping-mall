package com.edaochina.shopping.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @BelongsPackage: com.yidao.common.util
 * @Author: Jason
 * @CreateTime: 2018-10-20 16:39
 * @Description: 验证码工具类
 */
@UtilityClass
public class CodeUtil {
    /**
     * 生成验证码(四位)
     */
    public static String makeAuthCode() {
        return RandomStringUtils.random(4, "0123456789");
    }

    public static String makeAuthCode(int length) {
        return RandomStringUtils.random(length, "0123456789");
    }

    /**
     * 生成带字母验证码(六位)
     */
    public static String makeAuthCodeSix() {
        return RandomStringUtils.random(6, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }
}
