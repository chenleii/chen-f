package com.chen.f.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.core.pojo.SysDict;
import com.chen.f.core.pojo.enums.StatusEnum;
import com.chen.f.core.pojo.enums.SysDictTypeEnum;

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
     * @param code            字典标识
     * @param key             字典key
     * @param name            字典名称
     * @param value           字典值
     * @param remark          字典描述
     * @param color           字典颜色
     * @param sysDictTypeEnum 字典类型
     * @param statusEnum      字典状态
     * @return 分页的系统字典集合
     */
    IPage<SysDict> getSysDictPage(long pageIndex, long pageNumber,
                                  String code, String key, String name, String value, String remark, String color, SysDictTypeEnum sysDictTypeEnum, StatusEnum statusEnum);

    /**
     * 获取所有启用的系统字典列表
     *
     * @return 所有启用的系统字典列表
     */
    List<SysDict> getAllEnabledSysDictList();

    /**
     * 获取系统字典集合
     *
     * @param code 字典标识
     * @return 系统字典集合
     */
    List<SysDict> getSysDictList(String code);

    /**
     * 获取系统字典对象
     *
     * @param code 字典标识
     * @param key  字典key
     * @return 系统字典对象
     */
    SysDict getSysDict(String code, String key);

    /**
     * 创建系统字典
     *
     * @param code              字典标识
     * @param key               字典key
     * @param name              字典名称
     * @param value             字典值
     * @param remark            字典描述
     * @param color             字典颜色
     * @param type              字典类型
     * @param status            字典状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void createSysDict(String code, String key, String name, String value, String remark, String color, SysDictTypeEnum type,
                       StatusEnum status, String operatedSysUserId);

    /**
     * 修改系统字典
     *
     * @param code              字典标识
     * @param key               字典key
     * @param name              字典名称
     * @param value             字典值
     * @param remark            字典描述
     * @param color             字典颜色
     * @param type              字典类型
     * @param status            字典状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void updateSysDict(String code, String key, String name, String value, String remark, String color, SysDictTypeEnum type,
                       StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统字典
     *
     * @param code 字典标识
     */
    void deleteSysDict(String code);

    /**
     * 删除系统字典
     *
     * @param code 字典标识
     * @param key  字典key
     */
    void deleteSysDict(String code, String key);

    /**
     * 启用系统字典
     *
     * @param code 字典标识
     * @param key  字典key
     * @param operatedSysUserId 操作的系统用户id
     */
    void enabledSysDict(String code, String key, String operatedSysUserId);

    /**
     * 禁用系统字典
     *
     * @param code 字典标识
     * @param key  字典key
     * @param operatedSysUserId 操作的系统用户id
     */
    void disableSysDict(String code, String key, String operatedSysUserId);

}
