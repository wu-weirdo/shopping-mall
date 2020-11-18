package com.edaochina.shopping.api.service.common;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * CacheService
 *
 * @author wangpenglei
 * @since 2019/9/12 15:25
 */
public class CacheService {

    private CacheService() {

    }

    /**
     * 缓存获取
     *
     * @param k        缓存键
     * @param supplier 生成值
     * @return 值
     */
    public static <K, V> V get(K k, Supplier<V> supplier, RedisTemplate redisTemplate) {
        ValueOperations<K, V> valueOperations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(k)) {
            return valueOperations.get(k);
        }
        V v = supplier.get();
        valueOperations.set(k, v, 10, TimeUnit.MINUTES);
        return v;
    }

    /**
     * 缓存获取
     *
     * @param k        缓存键
     * @param supplier 生成值
     * @param minutes  缓存时长,单位分钟
     * @return 值
     */
    public static <K, V> V get(K k, Supplier<V> supplier, RedisTemplate redisTemplate, long minutes) {
        ValueOperations<K, V> valueOperations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(k)) {
            return valueOperations.get(k);
        }
        V v = supplier.get();
        valueOperations.set(k, v, minutes, TimeUnit.MINUTES);
        return v;
    }

    public static <K> boolean remove(K k, RedisTemplate redisTemplate) {
        return redisTemplate.hasKey(k) ? redisTemplate.delete(k) : true;
    }

}
