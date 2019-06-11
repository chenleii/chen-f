package com.chen.f.spring.boot.autoconfigure.datasource;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.config.ConfigFilter;
import com.alibaba.druid.filter.encoding.EncodingConvertFilter;
import com.alibaba.druid.filter.logging.CommonsLogFilter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.wall.WallFilter;
import com.chen.f.core.datasource.DynamicDataSource;
import com.chen.f.core.datasource.aop.DynamicDataSourceSwitchAspect;
import com.chen.f.spring.boot.autoconfigure.datasource.properties.DynamicDataSourceProperties;
import com.p6spy.engine.spy.P6DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author chen
 * @date 2018/10/22 10:18.
 */
@Configuration
@ConditionalOnClass({DruidDataSource.class, DruidDataSourceBuilder.class})
@ConditionalOnMissingBean({DataSource.class})
@AutoConfigureBefore({DruidDataSourceAutoConfigure.class, org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@ConditionalOnProperty(prefix = "chen.datasource.dynamic", name = "enable", havingValue = "true")
@EnableTransactionManagement
public class DataSourceAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceAutoConfiguration.class);

    private final DynamicDataSourceProperties properties;

    @Autowired
    public DataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    private final List<Filter> druidFilterList = new CopyOnWriteArrayList<>();

    @Autowired(required = false)
    public void addStatFilter(StatFilter statFilter) {
        this.druidFilterList.add(statFilter);
    }

    @Autowired(required = false)
    public void addConfigFilter(ConfigFilter configFilter) {
        this.druidFilterList.add(configFilter);
    }

    @Autowired(required = false)
    public void addEncodingConvertFilter(EncodingConvertFilter encodingConvertFilter) {
        this.druidFilterList.add(encodingConvertFilter);
    }

    @Autowired(required = false)
    public void addSlf4jLogFilter(Slf4jLogFilter slf4jLogFilter) {
        this.druidFilterList.add(slf4jLogFilter);
    }

    @Autowired(required = false)
    public void addLog4jFilter(Log4jFilter log4jFilter) {
        this.druidFilterList.add(log4jFilter);
    }

    @Autowired(required = false)
    public void addLog4j2Filter(Log4j2Filter log4j2Filter) {
        this.druidFilterList.add(log4j2Filter);
    }

    @Autowired(required = false)
    public void addCommonsLogFilter(CommonsLogFilter commonsLogFilter) {
        this.druidFilterList.add(commonsLogFilter);
    }

    @Autowired(required = false)
    public void addWallFilter(WallFilter wallFilter) {
        this.druidFilterList.add(wallFilter);
    }


    @Primary
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        Map<String, DruidDataSource> dataSourceMap = properties.getDatasource();
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultDataSourceKey(properties.getPrimary());
        //设置多个数据源
        for (Map.Entry<String, DruidDataSource> dataSourceEntry : dataSourceMap.entrySet()) {
            String druidDataSourceName = dataSourceEntry.getKey();
            DruidDataSource druidDataSource = dataSourceEntry.getValue();

            druidDataSource.setName(druidDataSourceName);
            druidDataSource.setProxyFilters(this.druidFilterList);
            try {
                // 初始化数据源
                druidDataSource.init();
            } catch (SQLException e) {
                logger.error("druid数据源初始化失败", e);
                throw new RuntimeException("druid数据源初始化失败", e);
            }
            dynamicDataSource.addDataSource(druidDataSourceName, druidDataSource);
        }

        // p6spy
        if (properties.getP6spy()) {
            try {
                Class.forName("com.p6spy.engine.spy.P6DataSource");
                logger.debug("数据源关联p6sy成功");
                return new P6DataSource(dynamicDataSource);
            } catch (Exception e) {
                logger.warn("开启了p6spy但并未引入相关依赖");
            }
        }
        return dynamicDataSource;
    }

    @Bean
    public DynamicDataSourceSwitchAspect dynamicDataSourceSwitchAspect() {
        DynamicDataSourceSwitchAspect dynamicDataSourceSwitchAspect = new DynamicDataSourceSwitchAspect();
        dynamicDataSourceSwitchAspect.setOrder(properties.getOrder());
        return dynamicDataSourceSwitchAspect;
    }
}
