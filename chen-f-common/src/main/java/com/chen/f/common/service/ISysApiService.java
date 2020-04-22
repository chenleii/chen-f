package com.chen.f.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.common.pojo.enums.SysApiTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统接口表 服务类
 * </p>
 *
 * @author chen
 * @since 2019-03-05
 */
public interface ISysApiService extends IService<SysApi> {


    /**
     * 获取所有的系统接口列表
     *
     * @return 所有的系统接口列表
     */
    List<SysApi> getAllSysApiList();

    /**
     * 获取启用的系统接口列表
     *
     * @return 启用的系统接口列表
     */
    List<SysApi> getEnabledSysApiList();
    
    /**
     * 获取分页的系统接口列表
     *
     * @param pageIndex  页数
     * @param pageNumber 页大小
     * @param name       系统接口名称
     * @param url        系统接口URL
     * @param httpMethod 系统接口HTTP请求方法
     * @param type       系统接口类型
     * @param remark     系统接口描述
     * @param status     系统接口状态
     * @return 分页的系统接口列表
     */
    IPage<SysApi> getSysApiPage(Long pageIndex, Long pageNumber, String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type,
                                String remark, StatusEnum status);
    
    /**
     * 获取系统接口
     *
     * @param sysApiId 系统接口ID
     * @return 系统接口
     */
    SysApi getSysApi(String sysApiId);

    /**
     * 创建系统接口
     *
     * @param name              接口名称
     * @param url               系统接口URL
     * @param httpMethod        系统接口HTTP请求方法
     * @param type              系统接口类型
     * @param remark            系统接口备注
     * @param status            系统接口状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void createSysApi(String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type, String remark,
                      StatusEnum status, String operatedSysUserId);


    /**
     * 修改系统接口
     *
     * @param sysApiId          系统接口id
     * @param name              接口名称
     * @param url               系统接口URL
     * @param httpMethod        系统接口HTTP请求方法
     * @param type              系统接口类型
     * @param remark            系统接口备注
     * @param status            系统接口状态
     * @param operatedSysUserId 操作的系统用户ID
     */
    void updateSysApi(String sysApiId, String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type, String remark,
                      StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统接口
     *
     * @param sysApiId 系统接口ID
     */
    void deleteSysApi(String sysApiId);

    /**
     * 启用系统接口
     *
     * @param sysApiId          系统接口ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void enabledSysApi(String sysApiId, String operatedSysUserId);

    /**
     * 禁用系统接口
     *
     * @param sysApiId          系统接口ID
     * @param operatedSysUserId 操作的系统用户ID
     */
    void disableSysApi(String sysApiId, String operatedSysUserId);

}
