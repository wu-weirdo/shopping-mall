package com.edaochina.shopping.common.utils;

import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import lombok.experimental.UtilityClass;
import org.springframework.util.ClassUtils;

/**
 * 反射工具类
 *
 * @author cdq
 */
@UtilityClass
public class ReflectUtil {

    /**
     * 实例化类对象
     *
     * @param className 类名
     * @return 实例对象
     * @throws Exception
     */
    public final Object newInstance(String className) throws Exception {
        return newInstance(findClass(className));
    }

    /**
     * 实例化类对象
     *
     * @param aClass java类
     * @return 实例对象
     * @throws Exception
     */
    public final Object newInstance(Class<?> aClass) {
        try {
            return aClass.newInstance();
        } catch (ReflectiveOperationException e) {
            // 实例化异常
            throw new YidaoException(ReturnData.REFLECT_ERROR.getCode(), ReturnData.REFLECT_ERROR.getMsg());
        }
    }

    /**
     * 根据类名（string）找java类
     *
     * @param className 类名（string）
     * @return java类
     * @throws Exception
     */
    public final Class<?> findClass(String className) throws Exception {
        try {
            return ClassUtils.forName(className.trim(),
                    Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new Exception("className" +
                    className);
        }
    }

}
