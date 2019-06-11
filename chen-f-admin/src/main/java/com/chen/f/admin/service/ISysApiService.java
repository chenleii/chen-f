package com.chen.f.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.f.core.pojo.SysApi;
import com.chen.f.core.pojo.enums.StatusEnum;
import com.chen.f.core.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.core.pojo.enums.SysApiTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统api表 服务类
 * </p>
 *
 * @author chen
 * @since 2019-03-05
 */
public interface ISysApiService extends IService<SysApi> {
    /**
     * 获取分页的系统API集合
     *
     * @param pageIndex  页数
     * @param pageNumber 页大小
     * @param name       系统API名称
     * @param url        系统API URL
     * @param httpMethod 系统API HTTP请求方法
     * @param type       系统API类型
     * @param remark     系统API描述
     * @param status     系统API状态
     * @return 分页的系统API集合
     */
    IPage<SysApi> getSysApiPage(long pageIndex, long pageNumber, String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type,
                                String remark, StatusEnum status);

    /**
     * 获取启用的系统API列表
     *
     * @return 启用的系统API列表
     */
    List<SysApi> getEnabledSysApiList();

    /**
     * 获取系统API
     *
     * @param sysApiId 系统APIid
     * @return 系统API
     */
    SysApi getSysApi(String sysApiId);


    /**
     * 创建系统API
     *
     * @param name              API名称
     * @param url               系统API URL
     * @param httpMethod        系统API HTTP请求方法
     * @param type              系统API类型
     * @param remark            备注
     * @param status            状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void createSysApi(String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type, String remark,
                      StatusEnum status, String operatedSysUserId);


    /**
     * 修改系统API
     *
     * @param sysApiId          系统APIid
     * @param name              API名称
     * @param url               系统API URL
     * @param httpMethod        系统API HTTP请求方法
     * @param type              系统API类型
     * @param remark            备注
     * @param status            状态
     * @param operatedSysUserId 操作的系统用户id
     */
    void updateSysApi(String sysApiId, String name, String url, SysApiHttpMethodEnum httpMethod, SysApiTypeEnum type, String remark,
                      StatusEnum status, String operatedSysUserId);

    /**
     * 删除系统API
     *
     * @param sysApiId          系统APIid
     */
    void deleteSysApi(String sysApiId);

    /**
     * 启用系统API
     *
     * @param sysApiId          系统APIid
     * @param operatedSysUserId 操作的系统用户id
     */
    void enabledSysApi(String sysApiId, String operatedSysUserId);

    /**
     * 禁用系统API
     *
     * @param sysApiId          系统APIid
     * @param operatedSysUserId 操作的系统用户id
     */
    void disableSysApi(String sysApiId, String operatedSysUserId);
}
