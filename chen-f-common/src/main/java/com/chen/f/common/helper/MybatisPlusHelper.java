package com.chen.f.common.helper;

/**
 * @author chen
 * @date 2018/10/26 15:11.
 */
public class MybatisPlusHelper {

    ///**
    // * 批量插入
    // * 要保证该实体在mybatisPlus中有对应关系
    // *
    // * @param entityList 实体集合
    // * @param batchSize  插入批次数量
    // * @param <T>        实体类型
    // * @return 受影响行数
    // */
    //public static <T> int insertBatch(List<T> entityList, int batchSize, Class<T> pojoClass) {
    //    if (CollectionUtils.isEmpty(entityList)) {
    //        throw new IllegalArgumentException("Error: entityList must not be empty");
    //    }
    //    if (pojoClass == null) {
    //        throw new IllegalArgumentException("Error: pojoClass must not be empty");
    //    }
    //
    //    String sqlStatement = SqlHelper.table(pojoClass).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
    //    int i = 0;
    //    try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(pojoClass)) {
    //        for (T anEntityList : entityList) {
    //            batchSqlSession.insert(sqlStatement, anEntityList);
    //            if (i >= 1 && i % batchSize == 0) {
    //                batchSqlSession.flushStatements();
    //            }
    //            i++;
    //        }
    //        batchSqlSession.flushStatements();
    //    }
    //    return i;
    //}
    //
    ///**
    // * 批量修改
    // * 要保证该实体在mybatisPlus中有对应关系
    // *
    // * @param entityList 实体集合
    // * @param batchSize  修改批次数量
    // * @param <T>        实体类型
    // * @return 受影响行数
    // */
    //public static <T> int updateBatchById(Collection<T> entityList, int batchSize) {
    //    if (CollectionUtils.isEmpty(entityList)) {
    //        throw new IllegalArgumentException("Error: entityList must not be empty");
    //    }
    //
    //    Class pojoClass = ReflectionKit.getSuperClassGenericType(entityList.getClass(), 0);
    //    String sqlStatement = SqlHelper.table(pojoClass).getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod());
    //    int i = 0;
    //    try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(pojoClass)) {
    //        for (T anEntityList : entityList) {
    //            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
    //            param.put(Constants.ENTITY, anEntityList);
    //            batchSqlSession.update(sqlStatement, param);
    //            if (i >= 1 && i % batchSize == 0) {
    //                batchSqlSession.flushStatements();
    //            }
    //            i++;
    //        }
    //        batchSqlSession.flushStatements();
    //    }
    //    return i;
    //}
}
