package com.chen.f.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysParameter;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysParameterTypeEnum;

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
     * 获取启用的系统参数列表
     *
     * @return 启用的系统参数列表
     */
    List<SysParameter> getEnabledSysParameterList();

    /**
     * 获取分页的系统参数集合
     *
     * @param pageIndex  页数
     * @param pageNumber 页大小
     * @param code       系统参数标识
     * @param name       系统参数名称
     * @param value      系统参数值
     * @param type       系统参数类型
     * @param remark     系统参数描述
     * @param statusEnum 系统参数状态
     * @return 分页的系统参数集合
     */
    IPage<SysParameter> getSysParameterPage(long pageIndex, long pageNumber,
                                            String code, String name, String value, SysParameterTypeEnum type, String remark, StatusEnum statusEnum);

    /**
     * 获取系统参数对象
     *
     * @param sysParameterId 系统参数ID
     * @return 系统参数对象
     */
    SysParameter getSysParameter(String sysParameterId);

    /**
     * 获取系统参数对象
     *
     * @param code 系统参数标识
     * @return 系统参数对象
     */
    SysParameter getSysParameterByCode(String code);

    /**
     * 创建系统参数
     *
     * @param code              系统参数标识
     * @param name              系统参数名称
     * @param value             系统参数值
     * @param type              系统参数类型
     * @param remark            系统参数描述
     * @param status            系统参数状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void createSysParameter(String code, String name, String value, SysParameterTypeEnum type, String remark,
                            StatusEnum status, String operatedSysUserId);

    /**
     * 修改系统参数
     *
     * @param code              系统参数标识
     * @param name              系统参数名称
     * @param value             系统参数值
     * @param type              系统参数类型
     * @param remark            系统参数描述
     * @param status            系统参数状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void updateSysParameter(String code, String name, String value, SysParameterTypeEnum type, String remark,
                            StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统参数
     *
     * @param code 系统参数标识
     */
    void deleteSysParameter(String code);


    /**
     * 启用系统参数
     *
     * @param code              系统参数标识
     * @param operatedSysUserId 操作的系统用户id
     */
    void enabledSysParameter(String code,  String operatedSysUserId);

    /**
     * 禁用系统参数
     *
     * @param code              系统参数标识
     * @param operatedSysUserId 操作的系统用户id
     */
    void disableSysParameter(String code, String operatedSysUserId);
}
