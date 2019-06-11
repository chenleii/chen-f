package com.chen.f.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.core.pojo.SysParameter;
import com.chen.f.core.pojo.enums.StatusEnum;
import com.chen.f.core.pojo.enums.SysParameterTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统参数表 服务类
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
public interface ISysParameterService extends IService<SysParameter> {

    /**
     * 获取所有启用的系统参数列表
     *
     * @return 所有启用的系统参数列表
     */
    List<SysParameter> getAllEnabledSysParameterList();

    /**
     * 获取分页的系统参数集合
     *
     * @param pageIndex  页数
     * @param pageNumber 页大小
     * @param code       参数标识
     * @param name       参数名称
     * @param value      参数值
     * @param type       参数类型
     * @param remark     参数描述
     * @param statusEnum 参数状态
     * @return 分页的系统参数集合
     */
    IPage<SysParameter> getSysParameterPage(long pageIndex, long pageNumber,
                                            String code, String name, String value, SysParameterTypeEnum type, String remark, StatusEnum statusEnum);

    /**
     * 获取系统参数对象
     *
     * @param code 参数标识
     * @return 系统参数对象
     */
    SysParameter getSysParameter(String code);

    /**
     * 创建系统参数
     *
     * @param code              参数标识
     * @param name              参数名称
     * @param value             参数值
     * @param type              参数类型
     * @param remark            参数描述
     * @param status            参数状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void createSysParameter(String code, String name, String value, SysParameterTypeEnum type, String remark,
                            StatusEnum status, String operatedSysUserId);

    /**
     * 修改系统参数
     *
     * @param code              参数标识
     * @param name              参数名称
     * @param value             参数值
     * @param type              参数类型
     * @param remark            参数描述
     * @param status            参数状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void updateSysParameter(String code, String name, String value, SysParameterTypeEnum type, String remark,
                            StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统参数
     *
     * @param code 参数标识
     */
    void deleteSysParameter(String code);


    /**
     * 启用系统参数
     *
     * @param code              参数标识
     * @param operatedSysUserId 操作的系统用户id
     */
    void enabledSysParameter(String code,  String operatedSysUserId);

    /**
     * 禁用系统参数
     *
     * @param code              参数标识
     * @param operatedSysUserId 操作的系统用户id
     */
    void disableSysParameter(String code, String operatedSysUserId);
}
