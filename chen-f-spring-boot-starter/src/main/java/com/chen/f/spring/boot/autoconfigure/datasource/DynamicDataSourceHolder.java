package com.chen.f.spring.boot.autoconfigure.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author chen
 * @date 2018/10/22 10:34.
 */
public class DynamicDataSourceHolder {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);

    private static final ThreadLocal<LinkedBlockingQueue<String>> THREAD_LOCAL_DATA_SOURCE_KEY = new InheritableThreadLocal<LinkedBlockingQueue<String>>() {
        @Override
        protected LinkedBlockingQueue<String> initialValue() {
            return new LinkedBlockingQueue<>();
        }
    };

    /**
     * 设置当前线程数据源
     *
     * @param dataSourceKey 数据源key
     */
    public static void setDataSourceKey(String dataSourceKey) {
        LinkedBlockingQueue<String> queue = THREAD_LOCAL_DATA_SOURCE_KEY.get();
        try {
            queue.put(dataSourceKey);
        } catch (InterruptedException e) {
            logger.error("设置数据源出现异常,后续数据库操作会被影响.", e);
            // 恢复中断状态
            Thread.currentThread().interrupt();
            throw new RuntimeException("设置数据源出现异常,后续数据库操作会被影响.", e);
        }

    }

    /**
     * 获得当前线程数据源
     *
     * @return 数据源key
     */
    public static String getDataSourceKey() {
        LinkedBlockingQueue<String> queue = THREAD_LOCAL_DATA_SOURCE_KEY.get();
        return queue.peek();
    }

    /**
     * 清空当前线程数据源
     * <p>
     * 如果当前线程是连续切换数据源
     * 只会移除掉当前线程的数据源名称
     * </p>
     */
    public static void removeDataSourceKey() {
        LinkedBlockingQueue<String> queue = THREAD_LOCAL_DATA_SOURCE_KEY.get();
        if (queue.isEmpty()) {
            THREAD_LOCAL_DATA_SOURCE_KEY.remove();
        } else {
            queue.poll();
        }
    }

    /**
     * 清空所有数据源
     * <p>
     * 如果当前线程是连续切换数据源
     * 也会移除掉所有线程的数据源名称
     * </p>
     */
    public static void removeAllDataSourceKey() {
        LinkedBlockingQueue<String> queue = THREAD_LOCAL_DATA_SOURCE_KEY.get();
        if (queue.isEmpty()) {
            THREAD_LOCAL_DATA_SOURCE_KEY.remove();
        } else {
            queue.clear();
            THREAD_LOCAL_DATA_SOURCE_KEY.remove();
        }
    }

}
