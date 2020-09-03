package com.chen.f.core.mybatisplus;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chen
 * @since 2020/9/4 0:22.
 */
public class Mappers {

    private static final Function<Class<?>, Class<?>> getModelClassFunction = (mapperClass) -> new AbstractSqlInjector() {
        @Override
        public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
            // 忽略
            return null;
        }

        public Class<?> getModelClass(Class<?> mapperClass) {
            return super.extractModelClass(mapperClass);
        }
    }.getModelClass(mapperClass);

    /**
     * 获取mapper代理类型的实体类型
     *
     * @param proxyClass mapper代理类型
     * @return 实体类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getProxyModelClass(Class<?> proxyClass) {
        return (Class<T>) getModelClassFunction.apply(getProxyMapperClass(proxyClass));
    }

    /**
     * 获取mapper代理类型的mapper类型
     *
     * @param proxyClass mapper代理类
     * @return mapper类型
     */
    public static Class<?> getProxyMapperClass(Class<?> proxyClass) {
        return proxyClass.getInterfaces()[0];
    }

    /**
     * 获取mapper类型的实体类型
     *
     * @param mapperClass mapper类型
     * @return 实体类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getMapperModelClass(Class<?> mapperClass) {
        return (Class<T>) getModelClassFunction.apply(mapperClass);
    }

    /**
     * 计算更新数量
     *
     * @param batchResultList 批量更新结果
     * @return 受影响行数
     */
    public static int calculateUpdateCounts(List<BatchResult> batchResultList) {
        if (CollectionUtils.isNotEmpty(batchResultList)) {
            return batchResultList.stream()
                    .filter(Objects::nonNull)
                    .map(BatchResult::getUpdateCounts)
                    .filter(Objects::nonNull)
                    .flatMapToInt(Arrays::stream)
                    .sum();
        }
        return 0;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    protected static Log log = LogFactory.getLog(Mappers.class);

    /**
     * 批量插入
     */
    public static <T> boolean insertBatch(Class<?> mapperClass, Class<T> entryClass, Collection<T> entityList, int batchSize) {
        String sqlStatement = SqlHelper.getSqlStatement(mapperClass, SqlMethod.INSERT_ONE);
        return SqlHelper.executeBatch(entryClass, log, entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    /**
     * 批量插入
     */
    public static <T> boolean updateBatchById(Class<?> mapperClass, Class<T> entryClass, Collection<T> entityList, int batchSize) {
        String sqlStatement = SqlHelper.getSqlStatement(mapperClass, SqlMethod.UPDATE_BY_ID);
        return SqlHelper.executeBatch(entryClass, log, entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
    }


    /**
     * 批量插入
     */
    public static <T> int insertBatchReturnCount(Class<?> mapperClass, Class<T> entryClass, Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return 0;
        }

        int updateCounts = 0;

        String sqlStatement = SqlHelper.getSqlStatement(mapperClass, SqlMethod.INSERT_ONE);
        final SqlSessionFactory sqlSessionFactory = SqlHelper.sqlSessionFactory(entryClass);
        try (
                SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        ) {
            final int size = entityList.size();
            int i = 1;
            for (T entry : entityList) {
                sqlSession.insert(sqlStatement, entry);
                if ((i % batchSize == 0) || i == size) {
                    List<BatchResult> batchResultList = sqlSession.flushStatements();
                    updateCounts += Mappers.calculateUpdateCounts(batchResultList);
                }
                i++;
            }
        }
        return updateCounts;
    }

    /**
     * 批量插入
     */
    public static <T> int updateBatchByIdReturnCount(Class<?> mapperClass, Class<T> entryClass, Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return 0;
        }

        int updateCounts = 0;

        String sqlStatement = SqlHelper.getSqlStatement(mapperClass, SqlMethod.UPDATE_BY_ID);
        final SqlSessionFactory sqlSessionFactory = SqlHelper.sqlSessionFactory(entryClass);
        try (
                SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        ) {
            final int size = entityList.size();
            int i = 1;
            for (T entry : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, entry);
                sqlSession.update(sqlStatement, param);
                if ((i % batchSize == 0) || i == size) {
                    List<BatchResult> batchResultList = sqlSession.flushStatements();
                    updateCounts += Mappers.calculateUpdateCounts(batchResultList);
                }
                i++;
            }
        }
        return updateCounts;
    }

    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
    private static final Map<Class<?>, Map<String, String>> MAP_CACHE = new ConcurrentHashMap<>();

    public static void entryPropertyConvertDbColumn(Class<?> entryClass, List<OrderItem> orderItemList) {
        final Map<String, String> map = MAP_CACHE.computeIfAbsent(entryClass, (key) -> {
            final TableInfo tableInfo = TableInfoHelper.getTableInfo(key);
            if (Objects.nonNull(tableInfo)) {
                final List<TableFieldInfo> tableFieldInfoList = tableInfo.getFieldList();
                if (CollectionUtils.isNotEmpty(tableFieldInfoList)) {
                    return tableFieldInfoList.stream()
                            .collect(Collectors.toMap(TableFieldInfo::getProperty, TableFieldInfo::getColumn));
                }
            }
            return Collections.emptyMap();
        });

        if (MapUtils.isEmpty(map)) {
            return;
        }
        
        if (CollectionUtils.isEmpty(orderItemList)) {
            return;
        }

        orderItemList.forEach((orderItem) -> {
            final String column = map.get(orderItem.getColumn());
            if (Objects.nonNull(column)) {
                orderItem.setColumn(column);
            }
        });
    }
}
