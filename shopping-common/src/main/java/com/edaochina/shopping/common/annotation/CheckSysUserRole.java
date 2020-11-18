package com.edaochina.shopping.common.annotation;

import java.lang.annotation.*;

/**
 * @author :         Jason
 * @createDate :     2018/10/15 19:09
 * @description :
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface CheckSysUserRole {
    String[] menuId() default {};
}
