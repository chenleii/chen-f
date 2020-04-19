package com.chen.f.core.api.response.success;


import java.util.HashMap;
import java.util.Map;

/**
 * 成功响应简单封装
 *
 * @author chen
 * @date 2018/10/29 19:07.
 */
public class R extends HashMap<String, Object> {

    public static Builder builder() {
        return new Builder();
    }

    public static R msg(Object value) {
        return builder().put("msg", value).build();
    }

    public static class Builder {
        private final Map<String, Object> map = new HashMap<>();

        public R build() {
            R r = new R();
            r.putAll(map);
            return r;
        }

        public Builder put(String key, Object value) {
            map.put(key, value);
            return this;
        }

        public Builder putAll(Map<String, Object> map) {
            map.putAll(map);
            return this;
        }

        public Builder clear() {
            map.clear();
            return this;
        }

    }


}
