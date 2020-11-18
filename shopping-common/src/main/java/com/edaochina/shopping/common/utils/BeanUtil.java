package com.edaochina.shopping.common.utils;

import com.edaochina.shopping.common.annotation.PropertyIgnoreVertify;
import com.edaochina.shopping.domain.constants.DateConstant;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :         Jason
 * @createDate :     2018/10/10 17:20
 * @description :
 */
@UtilityClass
public class BeanUtil {

    /**
     * 以 list 的形式转换领域模型
     *
     * @param sourceList 包含数据的领域模型数组
     * @param target     转换的领域模型类
     * @param <S>        包含数据的领域模型泛型
     * @param <T>        转换的领域模型类泛型
     * @return 转换并生成的领域模型数组
     * @throws IllegalAccessException 非法参数异常
     * @throws InstantiationException 无法实例化异常
     */
    public <S, T> List<T> listBeanToList(List<S> sourceList, Class<T> target)
            throws IllegalAccessException, InstantiationException {
        List<T> targetList = new ArrayList<>();
        for (S sourceBean : sourceList) {
            T targetBean = target.newInstance();
            BeanUtils.copyProperties(sourceBean, targetBean);
            targetList.add(targetBean);
        }
        return targetList;
    }

    /**
     * 参数合法校验
     * 只校验第一级属性且只校验类的属性，基本类型（未测试）
     * 如果还需要优化，升级到拦截器,做到无感知，强制对指定类型的所有参数强制校验
     *
     * @param object
     * @return
     */
    public boolean illegalParam(Object object) throws IllegalAccessException, NoSuchFieldException {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(PropertyIgnoreVertify.class)) {
                PropertyIgnoreVertify vertify = field.getAnnotation(PropertyIgnoreVertify.class);
                // 如果定义了条件，按照条件过滤
                if (StringUtils.isNotBlank(vertify.conditionProperty()) && StringUtils.isNotBlank(vertify.conditionValue())) {
                    Field specify = clazz.getDeclaredField(vertify.conditionProperty());
                    specify.setAccessible(true);
                    if (specify.get(object).equals(vertify.conditionValue())) {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            Object obj = field.get(object);
            if (obj == null || obj.toString().equals(DateConstant.EMPTY_STRING)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 参数合法校验
     * 如果不合法，直接抛异常
     *
     * @param object
     */
    public void illegalParamThrowException(Object object, String msg) {
        boolean result = true;
        try {
            result = illegalParam(object);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            result = false;
        }
        if (!result) {
            throw new IllegalArgumentException(
                    msg + ":" + "入参存在null值或者空值" + object.toString());
        }
    }


    /**
     * 参数合法校验
     * 如果不合法，直接抛异常
     *
     * @param object
     */
    public void illegalParamThrowException(Object object) {
        illegalParamThrowException(object, DateConstant.EMPTY_STRING);
    }

}
