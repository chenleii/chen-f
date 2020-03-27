package com.chen.f.common.mapper;

import com.chen.f.common.mybatisplus.SupperMapper;
import com.chen.f.common.pojo.SysOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 系统组织机构表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-03-25
 */
public interface SysOrganizationMapper extends SupperMapper<SysOrganization> {

    /**
     * 根据ID查询当前记录和当前记录的所有下级记录
     *
     * @param id ID
     * @return 当前记录和当前记录的所有下级记录
     */
    default List<SysOrganization> selectSubordinateById(@Param("id") String id) {
        return selectSubordinateByIdList(Collections.singletonList(id));
    }

    /**
     * 根据ID列表查询当前记录和当前记录的所有下级记录
     *
     * @param idList ID列表
     * @return 当前记录和当前记录的所有下级记录
     */
    List<SysOrganization> selectSubordinateByIdList(@Param("idList") List<String> idList);

    /**
     * 根据ID查询当前记录和当前记录的所有上级记录
     *
     * @param id ID
     * @return 当前记录和当前记录的所有上级记录
     */
    default List<SysOrganization> selectSuperiorById(@Param("id") String id) {
        return selectSuperiorByIdList(Collections.singletonList(id));
    }

    /**
     * 根据ID列表查询当前记录和当前记录的所有上级记录
     *
     * @param idList ID列表
     * @return 当前记录和当前记录的所有上级记录
     */
    List<SysOrganization> selectSuperiorByIdList(@Param("idList") List<String> idList);
}
