package com.chen.f.common.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.interfaces.Join;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author chen
 * @since 2018/11/3 22:10.
 */
public interface SupperMapper<T> extends BaseMapper<T> {

    /**
     * 共享锁
     */
    String LAST_SQL_LOCK_IN_SHARE_MODE = "LOCK IN SHARE MODE";

    /**
     * 排它锁
     */
    String LAST_SQL_FOR_UPDATE = "FOR UPDATE";

    /**
     * 默认批量大小
     */
    int DEFAULT_BATCH_SIZE = 100;


    /**
     * 查询所有记录
     *
     * @return 所有记录
     */
    default List<T> selectAll() {
        return selectList(null);
    }


    /**
     * 查询第一条记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 第一条记录
     */
    default T selectFirstOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        IPage<T> iPage = selectPage(new Page<>(1, 1, false), queryWrapper);
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            return iPage.getRecords().get(0);
        }
        return null;
    }

    /**
     * 查询一条记录并加排它锁
     * <p>
     * lastSql会被覆盖
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 记录
     */
    default T selectOneForUpdate(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = Wrappers.<T>lambdaQuery();
        }
        if (!(queryWrapper instanceof Join)) {
            throw new MybatisPlusException(" not support.");
        }
        ((Join<?>) queryWrapper).last(LAST_SQL_FOR_UPDATE);
        return selectOne(queryWrapper);
    }


    /**
     * 查询记录列表并加排它锁
     * <p>
     * lastSql会被覆盖
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 记录列表
     */
    default List<T> selectListForUpdate(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = Wrappers.<T>lambdaQuery();
        }
        if (!(queryWrapper instanceof Join)) {
            throw new MybatisPlusException(" not support.");
        }
        ((Join<?>) queryWrapper).last(LAST_SQL_FOR_UPDATE);
        return selectList(queryWrapper);
    }

    /**
     * 查询一条记录并加共享锁
     * <p>
     * lastSql会被覆盖
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 记录
     */
    default T selectOneLockInShareMode(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = Wrappers.<T>lambdaQuery();
        }
        if (!(queryWrapper instanceof Join)) {
            throw new MybatisPlusException(" not support.");
        }
        ((Join<?>) queryWrapper).last(LAST_SQL_LOCK_IN_SHARE_MODE);
        return selectOne(queryWrapper);
    }

    /**
     * 查询记录列表并加共享锁
     * <p>
     * lastSql会被覆盖
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 记录列表
     */
    default List<T> selectListLockInShareMode(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = Wrappers.<T>lambdaQuery();
        }
        if (!(queryWrapper instanceof Join)) {
            throw new MybatisPlusException(" not support.");
        }
        ((Join<?>) queryWrapper).last(LAST_SQL_LOCK_IN_SHARE_MODE);
        return selectList(queryWrapper);
    }


    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     * <p>
     * page Order 实体类字段名转数据库字段名
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    default <E extends com.chen.f.core.page.Page<T>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> mybatisPlusPage = page.toMybatisPlusPage();

        final List<OrderItem> orderItemList = mybatisPlusPage.getOrders();
        if (CollectionUtils.isNotEmpty(orderItemList)) {
            final TableInfo tableInfo = TableInfoHelper.getTableInfo(getCurrentEntityClass());
            if (Objects.nonNull(tableInfo)) {
                final List<TableFieldInfo> tableFieldInfoList = tableInfo.getFieldList();
                if (CollectionUtils.isNotEmpty(tableFieldInfoList)) {
                    orderItemList.forEach((orderItem -> {
                        final String column = tableFieldInfoList.stream()
                                .filter((tableFieldInfo -> Objects.equals(orderItem.getColumn(), tableFieldInfo.getProperty())))
                                .findFirst()
                                .map(TableFieldInfo::getColumn)
                                .orElse(null);

                        if (Objects.nonNull(column)) {
                            orderItem.setColumn(column);
                        }
                    }));
                }
            }
        }

        mybatisPlusPage = selectPage(mybatisPlusPage, queryWrapper);
        page.setList(mybatisPlusPage.getRecords());
        return page;
    }
    
    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     * <p>
     * page Order 实体类字段名转数据库字段名
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类
     */ 
    default <E extends com.chen.f.core.page.Page<Map<String, Object>>> E selectMapsPage(E page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Map<String, Object>> mybatisPlusPage = page.toMybatisPlusPage();

        final List<OrderItem> orderItemList = mybatisPlusPage.getOrders();
        if (CollectionUtils.isNotEmpty(orderItemList)) {
            final TableInfo tableInfo = TableInfoHelper.getTableInfo(getCurrentEntityClass());
            if (Objects.nonNull(tableInfo)) {
                final List<TableFieldInfo> tableFieldInfoList = tableInfo.getFieldList();
                if (CollectionUtils.isNotEmpty(tableFieldInfoList)) {
                    orderItemList.forEach((orderItem -> {
                        final String column = tableFieldInfoList.stream()
                                .filter((tableFieldInfo -> Objects.equals(orderItem.getColumn(), tableFieldInfo.getProperty())))
                                .findFirst()
                                .map(TableFieldInfo::getColumn)
                                .orElse(null);

                        if (Objects.nonNull(column)) {
                            orderItem.setColumn(column);
                        }
                    }));
                }
            }
        }

        mybatisPlusPage = selectMapsPage(mybatisPlusPage, queryWrapper);
        page.setList(mybatisPlusPage.getRecords());
        return page;
    }


    /**
     * 批量插入
     *
     * @param entityList 实体列表
     * @param batchSize  插入批次数量
     * @return 受影响行数
     */
    @Transactional(rollbackFor = Exception.class)
    default int insertBatch(List<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return 0;
        }

        int updateCounts = 0;

        Class<?> entryClass = getCurrentEntityClass();
        String sqlStatement = SqlHelper.table(entryClass).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
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
                    updateCounts += calculateUpdateCounts(batchResultList);
                }
                i++;
            }
        }
        return updateCounts;
    }

    /**
     * 批量修改
     *
     * @param entityList 实体列表
     * @param batchSize  修改批次数量
     * @return 受影响行数
     */
    @Transactional(rollbackFor = Exception.class)
    default int updateBatchById(List<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return 0;
        }

        int updateCounts = 0;

        Class<?> entryClass = getCurrentEntityClass();
        String sqlStatement = SqlHelper.table(entryClass).getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod());
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
                    updateCounts += calculateUpdateCounts(batchResultList);
                }
                i++;
            }
        }
        return updateCounts;
    }


    /**
     * 批量插入
     *
     * @param entityList 实体列表
     * @return 受影响行数
     */
    default int insertBatch(List<T> entityList) {
        return insertBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量修改
     *
     * @param entityList 实体列表
     * @return 受影响行数
     */
    default int updateBatchById(List<T> entityList) {
        return updateBatchById(entityList, DEFAULT_BATCH_SIZE);
    }


    /**
     * 获取当前mapper实体类
     *
     * @return class
     */
    default Class<T> getCurrentEntityClass() {
        return getEntityClass(this.getClass(), 0);
    }

    /**
     * 获取mapper的实体类class
     *
     * @param mapperClass mapper
     * @param index       实体类泛型下标
     * @return class
     */
    @SuppressWarnings("unchecked")
    default Class<T> getEntityClass(Class<?> mapperClass, int index) {
        final Type[] genericInterfaces = mapperClass.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                final ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                //if (parameterizedType.getRawType().equals(SupperMapper.class)) {
                return (Class<T>) parameterizedType.getActualTypeArguments()[index];
                //}
            } else if (genericInterface instanceof Class) {
                return getEntityClass((Class<?>) genericInterface, index);
            } else {
                return null;
            }
        }

        return null;
    }

    /**
     * 计算更新数量
     *
     * @param batchResultList 批量更新结果
     * @return 受影响行数
     */
    default int calculateUpdateCounts(List<BatchResult> batchResultList) {
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
}
