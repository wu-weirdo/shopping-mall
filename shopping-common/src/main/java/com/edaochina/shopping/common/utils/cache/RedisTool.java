package com.edaochina.shopping.common.utils.cache;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.common.frame.ServerInit;
import com.edaochina.shopping.common.utils.LoggerUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author :         Jason
 * @createDate :     2018/10/16 14:45
 * @description :
 */
@Component
public class RedisTool {
    /**
     * 静态 redis 类
     */
    private static StringRedisTemplate stringRedisTemplate;
    /**
     * 自动注入 redis 类
     */
    @Resource
    private StringRedisTemplate stringRedisTemplateResource;

    /**
     * 将数据存入缓存 不过期
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, String value) {
        LoggerUtils.info(RedisTool.class, "redis set 处理 key = " + key + ", value = " + value);
        stringRedisTemplate.delete(key);
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将数据存入缓存(带过期时间) 过期时间小于等于0则无限制
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间 秒
     */
    public static void set(String key, String value, long timeout) {
        LoggerUtils.info(RedisTool.class, "redis set 处理 key = " + key + ", value = " + value + ", 过期时间 " +
                timeout + "秒");
        stringRedisTemplate.delete(key);
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public static boolean lock(String key, long timeout) {
        // 利用lambda表达式
        return (Boolean) stringRedisTemplate.execute((RedisCallback) connection -> {
            long expireAt = System.currentTimeMillis() + timeout + 1;
            Boolean acquire = connection.setNX(key.getBytes(), String.valueOf(expireAt).getBytes());
            if (acquire) {
                return true;
            } else {
                byte[] value = connection.get(key.getBytes());
                if (Objects.nonNull(value) && value.length > 0) {
                    long expireTime = Long.parseLong(new String(value));
                    if (expireTime < System.currentTimeMillis()) {
                        // 如果锁已经过期
                        byte[] oldValue = connection.getSet(key.getBytes(), String.valueOf(System.currentTimeMillis() + timeout + 1).getBytes());
                        // 防止死锁
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

    public static boolean lock(String key) {
        return lock(key, 10000);
    }

    public static boolean unLock(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 刷新token
     */
    public static void expire(String key, Long value) {
        stringRedisTemplate.expire(key, value, TimeUnit.SECONDS);
    }

    /**
     * 获取数据缓存（字符串）
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        LoggerUtils.info(RedisTool.class, "redis get 处理 key = " + key + ", value = " + value);
        return value;
    }

    /**
     * 获取数据缓存（对象）
     *
     * @param key 键
     * @return 值
     */
    public static <T> T get(String key, Class<T> clazz) {
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s == null) {
            return null;
        }
        T value = JSONObject.parseObject(s, clazz);
        LoggerUtils.info(RedisTool.class, "redis get 处理 key = " + key + ", value = " + value);
        return value;
    }

    /**
     * 根据键值删除缓存信息
     *
     * @param key 键
     */
    public static void delete(String key) {
        LoggerUtils.info(RedisTool.class, "redis delete 处理 key = " + key);
        stringRedisTemplate.delete(key);
    }

    /**
     * 将对象转为json字符串存入缓存中(有效期1天)
     *
     * @param key   键
     * @param value 对象
     */
    public static void set(String key, Object value) {
        set(key, JSONObject.toJSONString(value), 60 * 60 * 24L);
    }

    /**
     * 注入后初始化
     */
    @PostConstruct
    public void init() {
        stringRedisTemplate = this.stringRedisTemplateResource;
        stringRedisTemplate.setEnableTransactionSupport(false);
        // 暂时这种处理方式
        ServerInit.initWorkId();
    }


}
