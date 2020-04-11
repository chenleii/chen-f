package com.chen.f.common.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.chen.f.common.mybatisplus.SupperMapper;
import com.chen.f.common.pojo.Region;
import com.chen.f.common.pojo.SysOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.Collections;
import java.util.List;


/**
 * <p>
 * 地区表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
public interface RegionMapper extends SupperMapper<Region> {

    /**
     * 根据ID查询当前记录和当前记录的所有下级记录
     *
     * @param id ID
     * @return 当前记录和当前记录的所有下级记录
     */
    default List<Region> selectSubordinateById(@Param("id") String id) {
        return selectSubordinateByIdList(Collections.singletonList(id), null);
    }

    /**
     * 根据ID查询当前记录和当前记录的所有下级记录
     *
     * @param id      ID
     * @param wrapper 实体对象封装操作类（可以为 null）
     * @return 当前记录和当前记录的所有下级记录
     */
    default List<Region> selectSubordinateById(@Param("id") String id, @Param(Constants.WRAPPER) Wrapper<SysOrganization> wrapper) {
        return selectSubordinateByIdList(Collections.singletonList(id), wrapper);
    }

    /**
     * 根据ID列表查询当前记录和当前记录的所有下级记录
     *
     * @param idList ID列表
     * @return 当前记录和当前记录的所有下级记录
     */
    default List<Region> selectSubordinateByIdList(@Param("idList") List<String> idList) {
        return selectSubordinateByIdList(idList, null);
    }

    /**
     * 根据ID列表查询当前记录和当前记录的所有下级记录
     *
     * @param idList  ID列表
     * @param wrapper 实体对象封装操作类（可以为 null）
     * @return 当前记录和当前记录的所有下级记录
     */
    List<Region> selectSubordinateByIdList(@Param("idList") List<String> idList, @Param(Constants.WRAPPER) Wrapper<SysOrganization> wrapper);

    /**
     * 根据ID查询当前记录和当前记录的所有上级记录
     *
     * @param id ID
     * @return 当前记录和当前记录的所有上级记录
     */
    default List<Region> selectSuperiorById(@Param("id") String id) {
        return selectSuperiorByIdList(Collections.singletonList(id), null);
    }

    /**
     * 根据ID查询当前记录和当前记录的所有上级记录
     *
     * @param id      ID
     * @param wrapper 实体对象封装操作类（可以为 null）
     * @return 当前记录和当前记录的所有上级记录
     */
    default List<Region> selectSuperiorById(@Param("id") String id, @Param(Constants.WRAPPER) Wrapper<SysOrganization> wrapper) {
        return selectSuperiorByIdList(Collections.singletonList(id), wrapper);
    }

    /**
     * 根据ID列表查询当前记录和当前记录的所有上级记录
     *
     * @param idList ID列表
     * @return 当前记录和当前记录的所有上级记录
     */
    default List<Region> selectSuperiorByIdList(@Param("idList") List<String> idList) {
        return selectSuperiorByIdList(idList, null);
    }

    /**
     * 根据ID列表查询当前记录和当前记录的所有上级记录
     *
     * @param idList  ID列表
     * @param wrapper 实体对象封装操作类（可以为 null）
     * @return 当前记录和当前记录的所有上级记录
     */
    List<Region> selectSuperiorByIdList(@Param("idList") List<String> idList, @Param(Constants.WRAPPER) Wrapper<SysOrganization> wrapper);
}
