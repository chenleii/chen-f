package com.chen.f.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysDictionary;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysDictionaryTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-04-06
 */
public interface ISysDictionaryService extends IService<SysDictionary> {
    /**
     * 获取分页的系统字典集合
     *
     * @param pageIndex             页数
     * @param pageNumber            页大小
     * @param code                  系统字典编码
     * @param name                  系统字典名称
     * @param remark                系统字典描述
     * @param sysDictionaryTypeEnum 系统字典类型
     * @param statusEnum            系统字典状态
     * @return 分页的系统字典集合
     */
    IPage<SysDictionary> getSysDictionaryPage(Long pageIndex, Long pageNumber,
                                              String code, String name, SysDictionaryTypeEnum sysDictionaryTypeEnum, String remark, StatusEnum statusEnum);

    /**
     * 获取启用的系统字典列表
     *
     * @return 启用的系统字典列表
     */
    List<SysDictionary> getEnabledSysDictionaryList();


    /**
     * 获取系统字典对象
     *
     * @param sysDictionaryId 系统字典ID
     * @return 系统字典对象
     */
    SysDictionary getSysDictionary(String sysDictionaryId);


    /**
     * 根据系统字典编码获取系统字典集合
     *
     * @param code 系统字典编码
     * @return 系统字典集合
     */
    SysDictionary getSysDictionaryByCode(String code);

    /**
     * 创建系统字典
     *
     * @param code              系统字典编码
     * @param name              系统字典名称
     * @param type              系统字典类型
     * @param remark            系统字典描述
     * @param status            系统字典状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void createSysDictionary(String code, String name, SysDictionaryTypeEnum type, String remark, StatusEnum status, String operatedSysUserId);

    /**
     * 修改系统字典
     *
     * @param sysDictionaryId   系统字典ID
     * @param code              系统字典编码
     * @param name              系统字典名称
     * @param type              系统字典类型
     * @param remark            系统字典描述
     * @param status            系统字典状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void updateSysDictionary(String sysDictionaryId, String code, String name, SysDictionaryTypeEnum type, String remark,
                             StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统字典
     *
     * @param sysDictionaryId 系统字典ID
     */
    void deleteSysDictionary(String sysDictionaryId);

    /**
     * 删除系统字典
     *
     * @param code 系统字典编码
     */
    void deleteSysDictionaryByCode(String code);

    /**
     * 启用系统字典
     *
     * @param sysDictionaryId   系统字典ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysDictionary(String sysDictionaryId, String operatedSysUserId);

    /**
     * 禁用系统字典
     *
     * @param sysDictionaryId   系统字典ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysDictionary(String sysDictionaryId, String operatedSysUserId);

}
