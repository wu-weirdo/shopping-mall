package com.edaochina.shopping.common.utils.idutil;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Jason
 * @CreateTime: 2018-10-20 12:16
 * @Description: Long型id生成工具
 */

public class IdUtils extends ServiceImpl {
    @Resource
    private AtomicLong id;

    private static volatile Long WORK_ID = 0L;
    private static volatile Long DATA_CENTER_ID = 0L;

    private static SnowFlake snowFlake = null;

    public IdUtils() {
    }

    public IdUtils(long initialId) {
        id = new AtomicLong(initialId);
    }

    /**
     * Long型id自增长生成类，支持多线程
     *
     * @return
     */
    public static Long nextLongId() {
        if (snowFlake == null) {
            snowFlake = new SnowFlake(WORK_ID, 0);
        }
        return snowFlake.nextId();
    }

    /**
     * Long型id自增长生成类，支持多线程
     *
     * @return
     */
    public static String nextId() {
        if (snowFlake == null) {
            snowFlake = new SnowFlake(WORK_ID, DATA_CENTER_ID);
        }
        return String.valueOf(snowFlake.nextId());
    }

    /**
     * 获取指定位数id
     *
     * @return
     */
    public static String nextId(int length) {
        return RandomStringUtils.random(length, "0123456789");
    }

    /**
     * Long型id自增长生成类
     *
     * @return
     */
    public Long next() {
        return id.incrementAndGet();
    }

    public static Long getWorkId() {
        return WORK_ID;
    }

    public static void setWorkId(Long workId) {
        WORK_ID = workId;
    }

    public static void setDataCenterId(Long dataCenterId) {
        DATA_CENTER_ID = dataCenterId;
    }
}
