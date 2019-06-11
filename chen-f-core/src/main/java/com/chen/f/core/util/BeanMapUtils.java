package com.chen.f.core.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * beanMap转换工具类
 *
 * @author chen
 * @since 2018/6/20 15:26.
 */
public class BeanMapUtils {

    /**
     * bean转map
     *
     * @param bean       bean对象
     * @param ignoreNull 是否忽略空
     * @return {@link Map<String,Object>}
     */
    public static Map<String, Object> beanToMap(final Object bean, final boolean ignoreNull) {
        try {
            final PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
            if (descriptors == null || descriptors.length == 0) {
                //为空直接返回map
                return Collections.emptyMap();
            }
            Map<String, Object> map = new HashMap<>();
            for (PropertyDescriptor descriptor : descriptors) {
                final String name = descriptor.getName();
                if (Objects.equals("class", name)) {
                    //getClass 过滤
                    continue;
                }
                if (PropertyUtils.isReadable(bean, name)) {
                    //没有get方法
                    continue;
                }
                Object property = PropertyUtils.getProperty(bean, name);
                if (property != null || !ignoreNull) {
                    map.put(name, property);
                }
            }
            return map;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * map转bean对象
     *
     * @param map       map对象
     * @param beanClass 转成bean对象class
     * @return bean对象
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
        try {
            if (MapUtils.isEmpty(map) || beanClass == null) {
                //map或beanClass为空
                return null;
            }
            T bean = beanClass.newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                final String name = entry.getKey();
                if (Objects.equals("class", name)) {
                    //class字段过滤
                    continue;
                }
                if (StringUtils.isBlank(name)) {
                    //过滤空字段名
                    continue;
                }
                if (PropertyUtils.isWriteable(bean, name)) {
                    PropertyUtils.setProperty(bean, name, entry.getValue());
                }
            }
            return bean;
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
