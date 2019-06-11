package com.chen.f.core.datasource;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author chen
 * @date 2018/10/22 10:35.
 */
@Slf4j
public class DynamicDataSource extends AbstractDataSource {

    /**
     * 分组标记
     */
    private static final String GROUP_MARK = "_";
    @Setter
    protected String defaultDataSourceKey;

    /**
     * 单数据源
     */
    private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();
    /**
     * 分组数据源
     */
    private Map<String, List<DataSource>> groupDataSourceMap = new ConcurrentHashMap<>();

    private DataSource determineGroupDataSource(List<DataSource> dataSourceList) {
        return dataSourceList.get(ThreadLocalRandom.current().nextInt(dataSourceList.size()));
    }

    private DataSource getDataSource(String dataSourceKey) {
        if (!groupDataSourceMap.isEmpty() && groupDataSourceMap.containsKey(dataSourceKey)) {
            log.debug("从 {} 组数据源中返回数据源", dataSourceKey);
            return determineGroupDataSource(groupDataSourceMap.get(dataSourceKey));
        }
        if (!dataSourceMap.isEmpty() && dataSourceMap.containsKey(dataSourceKey)) {
            log.debug("从 {} 单数据源中返回数据源", dataSourceKey);
            return dataSourceMap.get(dataSourceKey);
        }
        log.warn("没有找到数据源[{}]", dataSourceKey);
        throw new IllegalStateException("没有找到数据源[" + dataSourceKey + "]");
    }

    public DataSource determineDataSource() {
        String dataSourceKey = DynamicDataSourceHolder.getDataSourceKey();
        if (dataSourceKey == null || "".equals(dataSourceKey)) {
            log.debug("使用默认数据源");
            dataSourceKey = defaultDataSourceKey;
        }
        return getDataSource(dataSourceKey);
    }

    public synchronized void addDataSource(String dataSourceKey, DataSource dataSource) {
        Assert.notNull(dataSource, "不能添加空的数据源");
        dataSourceMap.put(dataSourceKey, dataSource);
        if (dataSourceKey != null && dataSourceKey.contains(GROUP_MARK)) {
            String group = dataSourceKey.split(GROUP_MARK)[0];
            if (groupDataSourceMap.containsKey(group)) {
                List<DataSource> dataSourceList = groupDataSourceMap.get(group);
                if (dataSourceList.contains(dataSource)) {
                    log.warn("添加数据源重复,这是必要的么?");
                }
                dataSourceList.add(dataSource);
            } else {
                List<DataSource> dataSourceList = new LinkedList<>();
                dataSourceList.add(dataSource);
                groupDataSourceMap.put(group, dataSourceList);
            }
        }
        log.info("添加数据源:[{}]成功", dataSourceKey);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineDataSource().getConnection(username, password);
    }
}
