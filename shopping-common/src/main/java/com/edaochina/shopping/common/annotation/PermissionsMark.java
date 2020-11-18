package com.edaochina.shopping.common.annotation;

import java.lang.annotation.*;

/**
 * 权限标记
 *
 * @author wangpenglei
 * @since 2019/10/11 15:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionsMark {

    int[] value();

}
