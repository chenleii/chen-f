package com.chen.f.common.mapper;

import com.chen.f.common.mybatisplus.SupperMapper;
import com.chen.f.common.pojo.Region;
import org.apache.ibatis.annotations.Param;

import java.util.Collections;
import java.util.List;


/**
 * <p>
 * 地区表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
public interface RegionMapper extends SupperMapper<Region> {

    /**
     * 根据ID查询当前记录和当前记录的所有下级记录
     *
     * @param id ID
     * @return 当前记录和当前记录的所有下级记录
     */
    default List<Region> selectSubordinateById(@Param("id") String id) {
        return selectSubordinateByIdList(Collections.singletonList(id));
    }

    /**
     * 根据ID列表查询当前记录和当前记录的所有下级记录
     *
     * @param idList ID列表
     * @return 当前记录和当前记录的所有下级记录
     */
    List<Region> selectSubordinateByIdList(@Param("idList") List<String> idList);

    /**
     * 根据ID查询当前记录和当前记录的所有上级记录
     *
     * @param id ID
     * @return 当前记录和当前记录的所有上级记录
     */
    default List<Region> selectSuperiorById(@Param("id") String id){
        return selectSuperiorByIdList(Collections.singletonList(id));
    }

    /**
     * 根据ID列表查询当前记录和当前记录的所有上级记录
     *
     * @param idList ID列表
     * @return 当前记录和当前记录的所有上级记录
     */
    List<Region> selectSuperiorByIdList(@Param("idList") List<String> idList);
}
