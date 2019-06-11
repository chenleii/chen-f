/**
 * Copyright © 2018 organization baomidou
 * <pre>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <pre/>
 */
package com.chen.f.spring.boot.autoconfigure.datasource.properties;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chen
 * @since 2018/12/15
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "chen.datasource.dynamic")
public class DynamicDataSourceProperties {

    /**
     * 是否开启动态数据源
     */
    private Boolean enable;
    /**
     * 必须设置默认的库,默认master
     */
    private String primary = "master";

    /**
     * aop切面顺序，默认优先级最高
     */
    private Integer order = Ordered.HIGHEST_PRECEDENCE;
    /**
     * 是否使用p6spy输出，默认不输出
     */
    private Boolean p6spy = false;

    /**
     * 每一个数据源
     */
    private Map<String, DruidDataSource> datasource = new LinkedHashMap<>();
}
