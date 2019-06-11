package com.chen.f.core.util;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author chen
 * @since 2018/12/14 15:42.
 */
public class ThreadPoolExecutorUtils {

    private static final ThreadPoolExecutor threadPoolExecutor;

    static {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("threadPool-%d").build();
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
        ThreadPoolExecutor.CallerRunsPolicy rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        threadPoolExecutor = new ThreadPoolExecutor(10, 100, 10, TimeUnit.MINUTES,
                blockingQueue, threadFactory, rejectedExecutionHandler);
    }

    public static void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }


    public static Future<?> submit(Runnable command) {
        return threadPoolExecutor.submit(command);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return threadPoolExecutor.submit(callable);
    }
}
