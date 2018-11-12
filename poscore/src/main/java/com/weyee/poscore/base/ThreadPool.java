package com.weyee.poscore.base;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liu-feng on 2017/6/6.
 */
public final class ThreadPool {
    private static ExecutorService EXECUTORS_INSTANCE;

    /**
     * 虚拟机获取系统返回的线程并发数
     * @return
     */
    private static Executor getExecutor() {
        if (EXECUTORS_INSTANCE == null) {
            synchronized (ThreadPool.class) {
                if (EXECUTORS_INSTANCE == null) {
                    EXECUTORS_INSTANCE = Executors.newFixedThreadPool(
                            Runtime.getRuntime().availableProcessors() > 0 ?
                                    Runtime.getRuntime().availableProcessors() : 2);
                }
            }
        }
        return EXECUTORS_INSTANCE;
    }

    public static void run(Runnable runnable) {
        getExecutor().execute(runnable);
    }
}
