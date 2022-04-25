package com.chen.f.admin.web.controller;

import com.chen.f.admin.configuration.security.SecurityHelper;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.common.pojo.enums.SysApiTypeEnum;
import com.chen.f.common.service.ISysApiService;
import com.chen.f.core.page.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统接口表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统接口接口")
@RestController
@RequestMapping("/chen/admin/sys/api")
public class SysApiController {
    protected static final Logger logger = LoggerFactory.getLogger(SysApiController.class);

    @Autowired
    private ISysApiService sysApiService;

    @ApiOperation(value = "获取所有的系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/all")
    public List<SysApi> getAllSysApiList() {
        return sysApiService.getAllSysApiList();
    }

    @ApiOperation(value = "获取启用的系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/all/enabled")
    public List<SysApi> getEnabledSysApiList() {
        return sysApiService.getEnabledSysApiList();
    }

    @ApiOperation(value = "获取分页的系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataTypeClass = Long.class, paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序信息(eg:name1.ascend-name2.descend)", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "系统接口名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "url", value = "系统接口URL", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "httpMethod", value = "系统接口HTTP请求方法", required = false, dataTypeClass = SysApiHttpMethodEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "系统接口类型", required = false, dataTypeClass = SysApiTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "系统接口备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "系统接口状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @ApiOperationSupport(ignoreParameters = {"list", "total", "orderList", "optimizeCountSql", "isSearchCount","searchCount", "hitCount",})
    @GetMapping
    public Page<SysApi> getSysApiPage(
            Page<SysApi> page,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "url", required = false) String url,
            @RequestParam(name = "httpMethod", required = false) SysApiHttpMethodEnum httpMethod,
            @RequestParam(name = "type", required = false) SysApiTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysApiService.getSysApiPage(page, name, url, httpMethod, type, remark, status);
    }

    @ApiOperation(value = "获取系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "系统接口Id", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysApiId}")
    public SysApi getSysApi(@PathVariable("sysApiId") String sysApiId) {
        return sysApiService.getSysApi(sysApiId);
    }

    @ApiOperation(value = "创建系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "系统接口名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "url", value = "系统接口URL", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "httpMethod", value = "系统接口HTTP请求方法", required = true, dataTypeClass = SysApiHttpMethodEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "系统接口类型", required = true, dataTypeClass = SysApiTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统接口备注", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统接口状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @PostMapping
    public void createSysApi(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "url") String url,
            @RequestParam(name = "httpMethod") SysApiHttpMethodEnum httpMethod,
            @RequestParam(name = "type") SysApiTypeEnum type,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysRoleId = SecurityHelper.getSysUserId();
        sysApiService.createSysApi(name, url, httpMethod, type, remark, status, operatedSysRoleId);
    }

    @ApiOperation(value = "创建系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysApi", value = "系统接口", required = true, dataTypeClass = SysApi.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createSysApi(@RequestBody() SysApi sysApi) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysApiService.createSysApi(sysApi.getName(), sysApi.getUrl(), sysApi.getHttpMethod(), sysApi.getType(),
                sysApi.getRemark(), sysApi.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "修改的系统接口ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "系统接口名称", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "url", value = "系统接口URL", required = true, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "httpMethod", value = "系统接口HTTP请求方法", required = true, dataTypeClass = SysApiHttpMethodEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "系统接口类型", required = true, dataTypeClass = SysApiTypeEnum.class, paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "系统接口备注", required = false, dataTypeClass = String.class, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "系统接口状态", required = true, dataTypeClass = StatusEnum.class, paramType = "form"),
    })
    @PutMapping("/{sysApiId}")
    public void updateSysApi(@PathVariable("sysApiId") String sysApiId,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "url") String url,
                             @RequestParam(name = "httpMethod") SysApiHttpMethodEnum httpMethod,
                             @RequestParam(name = "type") SysApiTypeEnum type,
                             @RequestParam(name = "remark", required = false) String remark,
                             @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysApiService.updateSysApi(sysApiId, name, url, httpMethod, type, remark, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "修改的系统接口ID", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysApi", value = "系统接口", required = true, dataTypeClass = SysApi.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysApiId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSysApi(@PathVariable("sysApiId") String sysApiId,
                             @RequestBody() SysApi sysApi) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysApiService.updateSysApi(sysApiId, sysApi.getName(), sysApi.getUrl(), sysApi.getHttpMethod(),
                sysApi.getType(), sysApi.getRemark(), sysApi.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "删除的系统接口ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysApiId}")
    public void deleteSysApi(@PathVariable("sysApiId") String sysApiId) {
        sysApiService.deleteSysApi(sysApiId);
    }

    @ApiOperation(value = "启用系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "系统接口Id", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysApiId}/enable")
    public void enabledSysApi(@PathVariable("sysApiId") String sysApiId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysApiService.enabledSysApi(sysApiId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统接口", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "系统接口ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysApiId}/disable")
    public void disableSysApi(@PathVariable("sysApiId") String sysApiId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysApiService.disableSysApi(sysApiId, operatedSysUserId);
    }
}
