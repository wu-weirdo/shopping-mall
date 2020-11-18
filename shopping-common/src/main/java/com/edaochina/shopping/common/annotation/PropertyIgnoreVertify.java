package com.edaochina.shopping.common.annotation;

import java.lang.annotation.*;

/**
 * Name
 * <p>
 * Date 2018-11-06
 * VersionV1.0
 *
 * @description
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PropertyIgnoreVertify {

    /**
     * 和 conditionValue 配套使用，如果该类的指定属性为指定的值则生效.
     * 不填全不生效
     *
     * @return
     */
    String conditionProperty() default "";

    String conditionValue() default "";
}
