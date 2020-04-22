package com.chen.f.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysDictionaryItem;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.TypeTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统字典项目表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-04-06
 */
public interface ISysDictionaryItemService extends IService<SysDictionaryItem> {
    /**
     * 获取分页的系统字典项目列表
     *
     * @param pageIndex       页数
     * @param pageNumber      页大小
     * @param sysDictionaryId 系统字典ID
     * @param code            系统字典项目编码
     * @param name            系统字典项目名称
     * @param key             系统字典项目KEY
     * @param value           系统字典项目值
     * @param valueTypeEnum   系统字典项目值类型
     * @param color           系统字典项目颜色
     * @param remark          系统字典项目描述
     * @param statusEnum      系统字典项目状态
     * @return 分页的系统字典项目列表
     */
    IPage<SysDictionaryItem> getSysDictionaryItemPage(Long pageIndex, Long pageNumber,
                                                      String sysDictionaryId, String code, String name, String key, String value, TypeTypeEnum valueTypeEnum, String color, String remark, StatusEnum statusEnum);

    /**
     * 获取启用的系统字典项目列表
     *
     * @return 启用的系统字典项目列表
     */
    List<SysDictionaryItem> getEnabledSysDictionaryItemListByCode();


    /**
     * 获取系统字典项目对象
     *
     * @param sysDictionaryItemId 系统字典项目ID
     * @return 系统字典项目对象
     */
    SysDictionaryItem getSysDictionaryItem(String sysDictionaryItemId);


    /**
     * 根据系统字典ID获取系统字典项目列表
     *
     * @param sysDictionaryId 系统字典ID
     * @return 系统字典项目列表
     */
    List<SysDictionaryItem> getSysDictionaryItemListBySysDictionaryId(String sysDictionaryId);

    /**
     * 根据系统字典编码获取系统字典项目列表
     *
     * @param code 系统字典编码
     * @return 系统字典项目列表
     */
    List<SysDictionaryItem> getSysDictionaryItemListByCode(String code);

    /**
     * 根据系统字典编码获取启用的系统字典项目列表
     *
     * @param code 系统字典编码
     * @return 系统字典项目列表
     */
    List<SysDictionaryItem> getEnabledSysDictionaryItemListByCode(String code);

    /**
     * 获取系统字典项目对象
     *
     * @param code 系统字典项目编码
     * @param key  系统字典项目KEY
     * @return 系统字典项目对象
     */
    SysDictionaryItem getSysDictionaryItemByCodeAndKey(String code, String key);

    /**
     * 创建系统字典项目
     *
     * @param sysDictionaryId   系统字典ID
     * @param code              系统字典项目编码
     * @param name              系统字典项目名称
     * @param key               系统字典项目KEY
     * @param value             系统字典项目值
     * @param valueI18n         系统字典项目值的国际化
     * @param keyType           系统字典项目KEY类型
     * @param valueType         系统字典项目值类型
     * @param color             系统字典项目颜色
     * @param order             系统字典项目顺序
     * @param remark            系统字典项目描述
     * @param status            系统字典项目状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void createSysDictionaryItem(String sysDictionaryId, String code, String name, String key, String value, String valueI18n, TypeTypeEnum keyType, TypeTypeEnum valueType, String color, Integer order, String remark,
                                 StatusEnum status, String operatedSysUserId);

    /**
     * 修改系统字典项目
     *
     * @param sysDictionaryItemId 系统字典项目ID
     * @param sysDictionaryId     系统字典ID
     * @param code                系统字典项目编码
     * @param name                系统字典项目名称
     * @param key                 系统字典项目KEY
     * @param value               系统字典项目值
     * @param valueI18n           系统字典项目值的国际化
     * @param keyType             系统字典项目KEY类型
     * @param valueType           系统字典项目值类型
     * @param color               系统字典项目颜色
     * @param order               系统字典项目顺序
     * @param remark              系统字典项目描述
     * @param status              系统字典项目状态
     * @param operatedSysUserId   操作的系统用户ID
     */
    void updateSysDictionaryItem(String sysDictionaryItemId, String sysDictionaryId, String code, String name, String key, String value, String valueI18n, TypeTypeEnum keyType, TypeTypeEnum valueType, String color, Integer order, String remark,
                                 StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统字典项目
     *
     * @param sysDictionaryItemId 系统字典项目ID
     */
    void deleteSysDictionaryItem(String sysDictionaryItemId);

    /**
     * 删除系统字典项目
     *
     * @param code 系统字典项目编码
     */
    void deleteSysDictionaryItemByCode(String code);

    /**
     * 删除系统字典项目
     *
     * @param code 系统字典项目编码
     * @param key  系统字典项目KEY
     */
    void deleteSysDictionaryItemByCodeAndKey(String code, String key);

    /**
     * 启用系统字典项目
     *
     * @param sysDictionaryItemId 系统字典项目ID
     * @param operatedSysUserId   操作的系统用户ID
     */
    void enabledSysDictionaryItem(String sysDictionaryItemId, String operatedSysUserId);

    /**
     * 启用系统字典项目
     *
     * @param code              系统字典项目编码
     * @param key               系统字典项目KEY
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysDictionaryItemByCodeAndKey(String code, String key, String operatedSysUserId);

    /**
     * 禁用系统字典项目
     *
     * @param sysDictionaryItemId 系统字典项目ID
     * @param operatedSysUserId   操作的系统用户ID
     */
    void disableSysDictionaryItem(String sysDictionaryItemId, String operatedSysUserId);

    /**
     * 禁用系统字典项目
     *
     * @param code              系统字典项目编码
     * @param key               系统字典项目KEY
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysDictionaryItemByCodeAndKey(String code, String key, String operatedSysUserId);
}
