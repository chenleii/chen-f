package com.chen.f.common.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
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
        IPage<T> iPage = selectPage( new Page<>(1, 1,false), queryWrapper);
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
    @SuppressWarnings("unchecked")
    default T selectOneForUpdate(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = Wrappers.<T>lambdaQuery();
        }
        AbstractWrapper abstractWrapper = (AbstractWrapper) queryWrapper;
        abstractWrapper.last(LAST_SQL_FOR_UPDATE);
        List<T> list = selectList(abstractWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询记录列表并加排它锁
     * <p>
     * lastSql会被覆盖
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 记录列表
     */
    @SuppressWarnings("unchecked")
    default List<T> selectListForUpdate(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = Wrappers.<T>lambdaQuery();
        }
        AbstractWrapper abstractWrapper = (AbstractWrapper) queryWrapper;
        abstractWrapper.last(LAST_SQL_FOR_UPDATE);
        return selectList(abstractWrapper);
    }

    /**
     * 查询一条记录并加共享锁
     * <p>
     * lastSql会被覆盖
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 记录
     */
    @SuppressWarnings("unchecked")
    default T selectOneLockInShareMode(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = Wrappers.<T>lambdaQuery();
        }
        AbstractWrapper abstractWrapper = (AbstractWrapper) queryWrapper;
        abstractWrapper.last(LAST_SQL_LOCK_IN_SHARE_MODE);
        List<T> list = selectList(abstractWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询记录列表并加共享锁
     * <p>
     * lastSql会被覆盖
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 记录列表
     */
    @SuppressWarnings("unchecked")
    default List<T> selectListLockInShareMode(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = Wrappers.<T>lambdaQuery();
        }
        AbstractWrapper abstractWrapper = (AbstractWrapper) queryWrapper;
        abstractWrapper.last(LAST_SQL_LOCK_IN_SHARE_MODE);
        return selectList(abstractWrapper);
    }

    /**
     * 批量插入
     *
     * @param entityList 实体集合
     * @param batchSize  插入批次数量
     * @return 受影响行数
     */
    @Transactional(rollbackFor = Exception.class)
    default int insertBatch(List<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            //throw new IllegalArgumentException("Error: entityList must not be empty");
            return 0;
        }

        Class<?> pojoClass = entityList.get(0).getClass();
        String sqlStatement = SqlHelper.table(pojoClass).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
        int updateCounts = 0;
        try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(pojoClass)) {
            for (int i = 0; i < entityList.size(); i++) {
                batchSqlSession.insert(sqlStatement, entityList.get(i));
                if (i >= 1 && i % batchSize == 0) {
                    List<BatchResult> batchResultList = batchSqlSession.flushStatements();
                    updateCounts += calculateUpdateCounts(batchResultList);
                }
            }
            List<BatchResult> batchResultList = batchSqlSession.flushStatements();
            updateCounts += calculateUpdateCounts(batchResultList);
        }
        return updateCounts;
    }

    /**
     * 批量修改
     *
     * @param entityList 实体集合
     * @param batchSize  修改批次数量
     * @return 受影响行数
     */
    @Transactional(rollbackFor = Exception.class)
    default int updateBatchById(List<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            //throw new IllegalArgumentException("Error: entityList must not be empty");
            return 0;
        }

        Class<?> pojoClass = entityList.get(0).getClass();
        String sqlStatement = SqlHelper.table(pojoClass).getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod());
        int updateCounts = 0;
        try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(pojoClass)) {
            for (int i = 0; i < entityList.size(); i++) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, entityList.get(i));
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    List<BatchResult> batchResultList = batchSqlSession.flushStatements();
                    updateCounts += calculateUpdateCounts(batchResultList);
                }
            }
            List<BatchResult> batchResultList = batchSqlSession.flushStatements();
            updateCounts += calculateUpdateCounts(batchResultList);
        }
        return updateCounts;
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

    /**
     * 批量插入
     *
     * @param entityList 实体集合
     * @return 受影响行数
     */
    default int insertBatch(List<T> entityList) {
        return insertBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量修改
     *
     * @param entityList 实体集合
     * @return 受影响行数
     */
    default int updateBatchById(List<T> entityList) {
        return updateBatchById(entityList, DEFAULT_BATCH_SIZE);
    }
}
