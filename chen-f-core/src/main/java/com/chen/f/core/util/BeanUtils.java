package com.chen.f.core.util;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author chen
 * @since 2017/11/16 10:29.
 */
public class BeanUtils {

    public static Map<String, Object> beanToMap(final Object bean) {
        return BeanUtils.beanToMap(bean, true);
    }

    /**
     * bean转map
     *
     * @param bean       需要转map的bean
     * @param ignoreNull 是否忽略空的值
     * @return map
     */
    public static Map<String, Object> beanToMap(final Object bean, final boolean ignoreNull) {
        Assert.notNull(bean, "bean == null");

        BeanMap beanMap = BeanMap.create(bean);
        HashMap<String, Object> map = new HashMap<>();

        Set set = beanMap.entrySet();
        for (Object o : set) {
            if (o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) o;
                //判断是否忽略空值
                if (ignoreNull) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        map.put(entry.getKey().toString(), entry.getValue());
                    }
                } else {
                    map.put(entry.getKey().toString(), entry.getValue());
                }
            }

        }
        return map;
    }

    public static Map<String, String> beanToMapStr(final Object bean) {
        return BeanUtils.beanToMapStr(bean, true);
    }

    /**
     * bean转map<string,string>
     *
     * @param bean 需要转map的bean
     * @return map
     */
    public static Map<String, String> beanToMapStr(final Object bean, final boolean ignoreNull) {
        Assert.notNull(bean, "bean == null");

        BeanMap beanMap = BeanMap.create(bean);
        HashMap<String, String> map = new HashMap<>();

        Set set = beanMap.entrySet();
        for (Object o : set) {
            if (o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) o;
                if (ignoreNull) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        map.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                } else {
                    map.put(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        }
        return map;
    }

    /**
     * map转bean
     *
     * @param map  需要转成bean的map
     * @param bean 要转成的bean
     * @return bean
     */
    public static <T> T mapToBean(final Map map, final T bean) {
        Assert.notNull(map, "map == null");
        Assert.notNull(bean, "bean == null");

        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);

        return bean;
    }
}
