package com.chen.f.admin.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.configuration.helper.SecurityHelper;
import com.chen.f.admin.service.ISysApiService;
import com.chen.f.admin.web.dto.SysApiInputDTO;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.common.pojo.enums.SysApiTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统API表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统API接口")
@RestController
@RequestMapping("/chen/admin/sys/api")
public class SysApiController {
    protected static final Logger logger = LoggerFactory.getLogger(SysApiController.class);

    @Autowired
    private ISysApiService sysApiService;

    @ApiOperation(value = "获取分页的系统API", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "url", value = "URL", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "httpMethod", value = "HTTP请求方法", required = false, dataTypeClass = SysApiHttpMethodEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", required = false, dataTypeClass = SysApiTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysApi> getSysApiPage(@RequestParam("pageIndex") long pageIndex,
                                       @RequestParam("pageNumber") long pageNumber,
                                       @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "url", required = false) String url,
                                       @RequestParam(name = "httpMethod", required = false) SysApiHttpMethodEnum httpMethod,
                                       @RequestParam(name = "type", required = false) SysApiTypeEnum type,
                                       @RequestParam(name = "remark", required = false) String remark,
                                       @RequestParam(name = "status", required = false) StatusEnum status) {
        return sysApiService.getSysApiPage(pageIndex, pageNumber, name, url, httpMethod, type, remark, status);
    }


    @ApiOperation(value = "获取启用的系统Api", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/all/enabled")
    public List<SysApi> getEnabledSysApiList() {
        return sysApiService.getEnabledSysApiList();
    }

    @ApiOperation(value = "获取系统Api", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "系统ApiId", required = true, dataTypeClass = String.class, paramType = "path")
    })
    @GetMapping("/{sysApiId}")
    public SysApi getSysApi(@PathVariable("sysApiId") String sysApiId) {
        return sysApiService.getSysApi(sysApiId);
    }

    @ApiOperation(value = "创建系统Api", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "url", value = "URL", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "httpMethod", value = "HTTP请求方法", required = true, dataTypeClass = SysApiHttpMethodEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataTypeClass = SysApiTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
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

    @ApiOperation(value = "创建系统Api", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysApiInputDTO", value = "系统Api", required = true, dataTypeClass = SysApiInputDTO.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void createSysApi(@RequestBody() SysApiInputDTO sysApiInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysApiService.createSysApi(sysApiInputDTO.getName(), sysApiInputDTO.getUrl(), sysApiInputDTO.getHttpMethod(), sysApiInputDTO.getType(),
                sysApiInputDTO.getRemark(), sysApiInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统Api", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "修改的系统Apiid", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "url", value = "URL", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "httpMethod", value = "HTTP请求方法", required = true, dataTypeClass = SysApiHttpMethodEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataTypeClass = SysApiTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
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

    @ApiOperation(value = "修改系统Api", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "修改的系统ApiId", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysApiInputDTO", value = "系统ApiDTO", required = true, dataTypeClass = SysApiInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{sysApiId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void updateSysApi(@PathVariable("sysApiId") String sysApiId,
                             @RequestBody() SysApiInputDTO sysApiInputDTO) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysApiService.updateSysApi(sysApiId, sysApiInputDTO.getName(), sysApiInputDTO.getUrl(), sysApiInputDTO.getHttpMethod(),
                sysApiInputDTO.getType(), sysApiInputDTO.getRemark(), sysApiInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统Api", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "删除的系统ApiId", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{sysApiId}")
    public void deleteSysApi(@PathVariable("sysApiId") String sysApiId) {
        sysApiService.deleteSysApi(sysApiId);
    }

    @ApiOperation(value = "启用系统Api", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "系统ApiId", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysApiId}/enable")
    public void enabledSysApi(@PathVariable("sysApiId") String sysApiId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysApiService.enabledSysApi(sysApiId, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统Api", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApiId", value = "系统ApiId", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{sysApiId}/disable")
    public void disableSysApi(@PathVariable("sysApiId") String sysApiId) {
        String operatedSysUserId = SecurityHelper.getSysUserId();
        sysApiService.disableSysApi(sysApiId, operatedSysUserId);
    }
}
