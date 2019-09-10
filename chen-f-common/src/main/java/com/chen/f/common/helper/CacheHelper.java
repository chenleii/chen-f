package com.chen.f.common.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * 缓存帮助类
 *
 * @author chen
 * @since 2019/9/10 14:10.
 */

public class CacheHelper {
    protected static final Logger logger = LoggerFactory.getLogger(CacheHelper.class);

    /**
     * 默认缓存名称
     */
    private static String defaultCacheName = "SYSTEM";

    /**
     * 缓存管理器
     */
    private static CacheManager cacheManager;

    public CacheHelper(CacheManager cacheManager) {
        CacheHelper.cacheManager = cacheManager;
    }

    /**
     * 获取缓存管理器
     *
     * @return 缓存管理器
     */
    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    /**
     * 获取缓存
     * <p>
     * 如果没有对应缓存使用系统默认缓存{@link #defaultCacheName);}
     *
     * @param cacheName 缓存名称
     * @return 缓存
     */
   public static Cache getOrDefaultCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            logger.warn("没有找到缓存[{}]使用系统默认缓存[{}]", cacheName, defaultCacheName);
            cache = cacheManager.getCache(defaultCacheName);
        }
        return cache;
    }

    /**
     * 添加缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存key
     * @param value     缓存值
     */
    public static void put(String cacheName, Object key, Object value) {
        getOrDefaultCache(cacheName).put(key, value);
    }

    /**
     * 获取缓存值
     *
     * @param cacheName 缓存名称
     * @param key       缓存key
     * @param <T>       缓存值泛型
     * @return 缓存值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String cacheName, Object key) {
        Cache.ValueWrapper valueWrapper = getOrDefaultCache(cacheName).get(key);
        if (valueWrapper != null) {
            Object o = valueWrapper.get();
            if (o != null) {
                return (T) o;
            }
        }
        return null;

    }

    /**
     * 获取缓存值
     *
     * @param cacheName 缓存名称
     * @param key       缓存key
     * @param type      缓存值类型
     * @param <T>       缓存值泛型
     * @return 缓存值
     */
    public static <T> T get(String cacheName, Object key, Class<T> type) {
        return getOrDefaultCache(cacheName).get(key, type);
    }

    /**
     * 删除缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存key
     */
    public static void remove(String cacheName, Object key) {
        getOrDefaultCache(cacheName).evict(key);
    }

    /**
     * 删除所有缓存
     *
     * @param cacheName 缓存名称
     */
    public static void removeAll(String cacheName) {
        getOrDefaultCache(cacheName).clear();
    }

}
