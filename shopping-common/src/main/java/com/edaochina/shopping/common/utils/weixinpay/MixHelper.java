package com.edaochina.shopping.common.utils.weixinpay;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 常用开发工具类
 *
 * @author zzk
 * @created 2018-12-12 18:57:56
 * @since v1.0
 */
public class MixHelper {

    private static final Logger log = LoggerFactory.getLogger(MixHelper.class);

    /**
     * 打印对象到Console，支持数组、Collection.
     *
     * @param input
     * @author zzk
     * @created 2018-12-12 18:57:56
     * @since v1.1.1
     */
    public static <T> void print(T input) {
        if (input == null) {
            System.err.println("input Object is null");
            return;
        }

        Object[] objArray = null;
        if (input instanceof Collection) {
            objArray = ((Collection) input).toArray();
        } else if (input.getClass().isArray()) {
            objArray = (Object[]) input;
        } else {
            System.out.println(input.toString());
            return;
        }

        System.out.println(Arrays.toString(objArray));
    }

    /**
     * 打印对象到Console，使用slf4j的方式.
     *
     * @param pattern :如 "KISS principle means Keep it {}, {}!"
     * @param args
     * @author zzk
     * @created 2018-12-12 18:57:56
     * @since v1.1.1
     */
    public static void print(String pattern, Object... args) {
        System.out.println(format(pattern, args));
    }

    /**
     * 使用slf4j的方式优雅地格式化字符串.
     *
     * @param pattern :如 "KISS principle means Keep it {}, {}!"
     * @param args
     * @author zzk
     * @created 2018-12-12 18:57:56
     * @since v1.1.1
     */
    public static String format(String pattern, Object... args) {
        return MessageFormatter.arrayFormat(pattern, args).getMessage();
    }

    /**
     * 暂停当前线程指定毫秒.
     *
     * @param millis
     * @author Jack Zhao
     * @created 2018-12-12 18:57:56
     * @since Common 0.1
     */
    public static void pause(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 暂停当前线程指定的时间.
     *
     * @param tu
     * @param num
     * @author zzk
     * @created 2018-12-12 18:57:56
     * @since v0.8.8
     */
    public static void pause(TimeUnit tu, int num) {
        try {
            tu.sleep(num);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static boolean isNotEmpty(Object input) {
        return !isEmpty(input);
    }

    public static boolean isEmpty(Object input) {
        if (input == null) {
            return true;
        }
        if (input instanceof Collection) {
            return CollectionUtils.isEmpty((Collection) input);
        } else if (input instanceof Map) {
            return MapUtils.isEmpty((Map) input);
        } else if (input instanceof String) {
            return StringUtils.isBlank((String) input);
        } else if (input.getClass().isArray()) {
            return ArrayUtils.isEmpty((Object[]) input);
        } else if (input instanceof Number) {
            try {
                return ((Long) input == 0);
            } catch (Exception e) {
                return ((Integer) input == 0);
            }
        } else {
            throw new RuntimeException(
                    "Input Type not supported,input=" + input.toString() + ",type=" + input.getClass());
        }
    }

    /**
     * 深拷贝泛型元素
     *
     * @param element
     * @return
     * @author zzk
     * @created 2018-12-12 18:57:56
     * @since v0.4.7
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T element) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(element);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}
