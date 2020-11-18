package com.edaochina.shopping.common.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 * 写这个类目的，由于这个项目在一个服务器手上运行,
 * 线程池统一管理，不使用Executors这种方式创建，
 * 不然coreThreads数量无法做到保证系统最佳运行状态
 *
 * @author jintian
 * @date 2019/8/19 16:42
 */
public class ThreadUtils {

    /**
     * 线程池 核心线程池数量1，最大数线程数10个，保持连接时间5分钟
     */
    private static Executor executor = new ThreadPoolExecutor(1, 10, 300, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
