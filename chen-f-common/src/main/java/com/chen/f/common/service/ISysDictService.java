package com.chen.f.common.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysDict;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysDictTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author chen
 * @since 2018-11-10
 */
public interface ISysDictService extends IService<SysDict> {
    /**
     * 获取分页的系统字典集合
     *
     * @param pageIndex       页数
     * @param pageNumber      页大小
     * @param code            系统字典标识
     * @param key             系统字典标识
     * @param name            系统字典名称
     * @param value           系统字典值
     * @param remark          系统字典描述
     * @param color           系统字典颜色
     * @param sysDictTypeEnum 系统字典类型
     * @param statusEnum      系统字典状态
     * @return 分页的系统字典集合
     */
    IPage<SysDict> getSysDictPage(Long pageIndex, Long pageNumber,
                                  String code, String key, String name, String value, String remark, String color, SysDictTypeEnum sysDictTypeEnum, StatusEnum statusEnum);

    /**
     * 获取启用的系统字典列表
     *
     * @return 启用的系统字典列表
     */
    List<SysDict> getEnabledSysDictList();


    /**
     * 获取系统字典对象
     *
     * @param sysDictId 系统字典ID
     * @return 系统字典对象
     */
    SysDict getSysDict(String sysDictId);


    /**
     * 获取系统字典集合
     *
     * @param code 系统字典标识
     * @return 系统字典集合
     */
    List<SysDict> getSysDictList(String code);

    /**
     * 获取系统字典对象
     *
     * @param code 系统字典标识
     * @param key  系统字典标识
     * @return 系统字典对象
     */
    SysDict getSysDict(String code, String key);

    /**
     * 创建系统字典
     *
     * @param code              系统字典标识
     * @param key               系统字典标识
     * @param name              系统字典名称
     * @param value             系统字典值
     * @param remark            系统字典描述
     * @param color             系统字典颜色
     * @param type              系统字典类型
     * @param order             系统字典顺序
     * @param status            系统字典状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void createSysDict(String code, String key, String name, String value, String remark, String color, SysDictTypeEnum type,
                       Integer order, StatusEnum status, String operatedSysUserId);

    /**
     * 修改系统字典
     *
     * @param sysDictId         系统字典ID
     * @param code              系统字典标识
     * @param key               系统字典标识
     * @param name              系统字典名称
     * @param value             系统字典值
     * @param remark            系统字典描述
     * @param color             系统字典颜色
     * @param type              系统字典类型
     * @param order             系统字典顺序
     * @param status            系统字典状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void updateSysDict(String sysDictId, String code, String key, String name, String value, String remark, String color, SysDictTypeEnum type,
                       Integer order, StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统字典
     *
     * @param sysDictId 系统字典ID
     */
    void deleteSysDict(String sysDictId);

    /**
     * 删除系统字典
     *
     * @param code 系统字典标识
     */
    void deleteSysDictByCode(String code);

    /**
     * 删除系统字典
     *
     * @param code 系统字典标识
     * @param key  系统字典标识
     */
    void deleteSysDictByCodeAndKey(String code, String key);

    /**
     * 启用系统字典
     *
     * @param sysDictId         系统字典ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysDict(String sysDictId, String operatedSysUserId);

    /**
     * 启用系统字典
     *
     * @param code              系统字典标识
     * @param key               系统字典标识
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysDictByCodeAndKey(String code, String key, String operatedSysUserId);

    /**
     * 禁用系统字典
     *
     * @param sysDictId         系统字典ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysDict(String sysDictId, String operatedSysUserId);

    /**
     * 禁用系统字典
     *
     * @param code              系统字典标识
     * @param key               系统字典标识
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysDictByCodeAndKey(String code, String key, String operatedSysUserId);

}
