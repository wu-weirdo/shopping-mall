package com.edaochina.shopping.common.annotation;

import java.lang.annotation.*;

/**
 * 操作记录标记注解
 *
 * @author wangpenglei
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogMark {

    String value() default "";

    String remark() default "";

}
