package com.chen.f.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.f.common.service.ISysPermissionService;
import com.chen.f.core.api.ApiAssert;
import com.chen.f.core.api.response.error.ErrorResponse;
import com.chen.f.common.mapper.SysPermissionApiMapper;
import com.chen.f.common.mapper.SysPermissionMapper;
import com.chen.f.common.mapper.SysPermissionMenuMapper;
import com.chen.f.common.mapper.SysRolePermissionMapper;
import com.chen.f.common.mapper.SysUserMapper;
import com.chen.f.common.pojo.SysPermission;
import com.chen.f.common.pojo.SysPermissionApi;
import com.chen.f.common.pojo.SysPermissionMenu;
import com.chen.f.common.pojo.SysRolePermission;
import com.chen.f.common.pojo.SysUser;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysPermissionTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统权限表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    protected static final Logger logger = LoggerFactory.getLogger(SysPermissionServiceImpl.class);
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysPermissionApiMapper sysPermissionApiMapper;
    @Autowired
    private SysPermissionMenuMapper sysPermissionMenuMapper;

    @Override
    public IPage<SysPermission> getSysPermissionPage(Long pageIndex, Long pageNumber,
                                                     String code, String name, SysPermissionTypeEnum type, String remark, StatusEnum status) {
        LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.<SysPermission>lambdaQuery();
        queryWrapper.eq(StringUtils.isNotBlank(code), SysPermission::getCode, code);
        queryWrapper.eq(StringUtils.isNotBlank(name), SysPermission::getName, name);
        queryWrapper.eq(Objects.nonNull(type), SysPermission::getType, type);
        queryWrapper.like(StringUtils.isNotBlank(remark), SysPermission::getRemark, remark);
        queryWrapper.eq(Objects.nonNull(status), SysPermission::getStatus, status);
        return sysPermissionMapper.selectPage(new Page<>(pageIndex, pageNumber), queryWrapper);
    }

    @Override
    public List<SysPermission> getEnabledSysPermissionList() {
        return sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getStatus, StatusEnum.ENABLED));
    }

    @Override
    public SysPermission getSysPermission(String sysPermissionId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限ID不能为空"));
        return sysPermissionMapper.selectById(sysPermissionId);
    }


    @Override
    public void createSysPermission(String code, String name, SysPermissionTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统权限编码不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统权限名称不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统权限类型不能为空"));
        //ApiAssert.isNotNull(remark, ErrorResponse.create("系统权限备注不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统权限状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("插入系统权限");
        SysPermission sysPermission = new SysPermission();
        sysPermission.setCode(code);
        sysPermission.setName(name);
        sysPermission.setRemark(remark);
        sysPermission.setType(type);
        sysPermission.setStatus(status);
        sysPermission.setUpdatedSysUserId(operatedSysUserId);
        sysPermission.setCreatedSysUserId(operatedSysUserId);
        sysPermission.setUpdatedDateTime(LocalDateTime.now());
        sysPermission.setCreatedDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.insert(sysPermission);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("创建系统权限失败"));
    }

    @Override
    public void updateSysPermission(String sysPermissionId, String code, String name, SysPermissionTypeEnum type, String remark, StatusEnum status, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限ID不能为空"));
        ApiAssert.isNotBlank(code, ErrorResponse.create("系统权限编码不能为空"));
        ApiAssert.isNotBlank(name, ErrorResponse.create("系统权限名称不能为空"));
        ApiAssert.isNotNull(type, ErrorResponse.create("系统权限类型不能为空"));
        //ApiAssert.isNotBlank(remark, ErrorResponse.create("系统权限类型不能为空"));
        ApiAssert.isNotNull(status, ErrorResponse.create("系统权限状态不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("系统权限不存在"));

        logger.debug("修改系统权限");
        sysPermission.setCode(code);
        sysPermission.setName(name);
        sysPermission.setRemark(remark);
        sysPermission.setType(type);
        sysPermission.setStatus(status);
        sysPermission.setUpdatedSysUserId(operatedSysUserId);
        sysPermission.setUpdatedDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.updateById(sysPermission);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统权限失败"));
    }

    @Override
    public void setSysApiOfSysPermission(String sysPermissionId, List<String> sysApiIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("设置的系统权限ID不能为空"));
        //ApiAssert.isNotEmpty(sysMenuIdList, ErrorResponse.create("设置的系统接口ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("系统权限不存在"));
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        List<SysPermissionApi> sysPermissionApiList = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(sysApiIdList)) {
            //转换
            sysPermissionApiList = sysApiIdList.stream()
                    .map((sysApiId) -> {
                        SysPermissionApi sysPermissionApi = new SysPermissionApi();
                        sysPermissionApi.setSysPermissionId(sysPermissionId);
                        sysPermissionApi.setSysApiId(sysApiId);
                        sysPermissionApi.setCreatedSysUserId(operatedSysUserId);
                        sysPermissionApi.setCreatedDateTime(LocalDateTime.now());
                        return sysPermissionApi;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统权限[{}]所有系统接口关联", sysPermissionId);
        sysPermissionApiMapper.delete(Wrappers.<SysPermissionApi>lambdaQuery().eq(SysPermissionApi::getSysPermissionId, sysPermissionId));
        logger.debug("插入系统权限接口关联");
        sysPermissionApiMapper.insertBatch(sysPermissionApiList);
    }

    @Override
    public void setSysMenuOfSysPermission(String sysPermissionId, List<String> sysMenuIdList, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("设置的系统权限ID不能为空"));
        //ApiAssert.isNotEmpty(sysMenuIdList, ErrorResponse.create("设置的系统菜单ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));
        
        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("修改系统权限不存在"));
        
        List<SysPermissionMenu> sysPermissionMenuList = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(sysMenuIdList)) {
            //转换
            sysPermissionMenuList = sysMenuIdList.stream()
                    .map((sysMenuId) -> {
                        SysPermissionMenu sysPermissionMenu = new SysPermissionMenu();
                        sysPermissionMenu.setSysPermissionId(sysPermissionId);
                        sysPermissionMenu.setSysMenuId(sysMenuId);
                        sysPermissionMenu.setCreatedSysUserId(operatedSysUserId);
                        sysPermissionMenu.setCreatedDateTime(LocalDateTime.now());
                        return sysPermissionMenu;
                    }).collect(Collectors.toList());
        }
        logger.debug("删除系统权限[{}]所有系统菜单关联", sysPermissionId);
        sysPermissionMenuMapper.delete(Wrappers.<SysPermissionMenu>lambdaQuery().eq(SysPermissionMenu::getSysPermissionId, sysPermissionId));
        logger.debug("插入系统权限接口关联");
        sysPermissionMenuMapper.insertBatch(sysPermissionMenuList);
    }

    @Override
    public void deleteSysPermission(String sysPermissionId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限ID不能为空"));
        
        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("修改系统权限不存在"));
       
        logger.debug("删除系统权限");
        int i = sysPermissionMapper.deleteById(sysPermissionId);
        ApiAssert.isEqualToOne(i, ErrorResponse.create("删除系统权限失败"));

        //删除系统角色权限
        sysRolePermissionMapper.delete(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getSysPermissionId, sysPermissionId));

        //删除系统权限菜单
        sysPermissionMenuMapper.delete(Wrappers.<SysPermissionMenu>lambdaQuery().eq(SysPermissionMenu::getSysPermissionId, sysPermissionId));

        //删除系统权限接口
        sysPermissionApiMapper.delete(Wrappers.<SysPermissionApi>lambdaQuery().eq(SysPermissionApi::getSysPermissionId, sysPermissionId));
    }

    @Override
    public void enabledSysPermission(String sysPermissionId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));

        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("修改系统权限不存在"));
        
        logger.debug("启用系统权限");
        sysPermission.setStatus(StatusEnum.ENABLED);
        sysPermission.setUpdatedSysUserId(operatedSysUserId);
        sysPermission.setUpdatedDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.update(sysPermission, Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getId, sysPermissionId).ne(SysPermission::getStatus, StatusEnum.ENABLED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统权限失败"));
    }

    @Override
    public void disableSysPermission(String sysPermissionId, String operatedSysUserId) {
        ApiAssert.isNotBlank(sysPermissionId, ErrorResponse.create("系统权限ID不能为空"));
        ApiAssert.isNotBlank(operatedSysUserId, ErrorResponse.create("操作的系统用户ID不能为空"));
        
        logger.debug("检查操作的系统用户是否存在");
        SysUser operatedSysUser = sysUserMapper.selectById(operatedSysUserId);
        ApiAssert.isNotNull(operatedSysUser, ErrorResponse.create("操作的系统用户不存在"));

        logger.debug("检查系统权限是否存在");
        SysPermission sysPermission = sysPermissionMapper.selectById(sysPermissionId);
        ApiAssert.isNotNull(sysPermission, ErrorResponse.create("修改系统权限不存在"));
        
        logger.debug("禁用系统权限");
        sysPermission.setStatus(StatusEnum.DISABLED);
        sysPermission.setUpdatedSysUserId(operatedSysUserId);
        sysPermission.setUpdatedDateTime(LocalDateTime.now());
        int i = sysPermissionMapper.update(sysPermission, Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getId, sysPermissionId).ne(SysPermission::getStatus, StatusEnum.DISABLED));
        ApiAssert.isEqualToOne(i, ErrorResponse.create("修改系统权限失败"));
    }
}
