package com.chen.f.admin.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.f.admin.security.Securitys;
import com.chen.f.admin.web.dto.SysDictInputDTO;
import com.chen.f.common.pojo.SysDict;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysDictTypeEnum;
import com.chen.f.common.service.ISysDictService;
import com.chen.f.common.web.dto.AlainSelectOutputDTO;
import com.chen.f.common.web.dto.AlainTagOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Api(tags = "系统字典接口")
@RestController
@RequestMapping("/chen/admin/sys/dict")
public class SysDictController {
    protected static final Logger logger = LoggerFactory.getLogger(SysDictController.class);

    @Autowired
    private ISysDictService sysDictService;

    @ApiOperation(value = "获取分页的系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true, dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "页大小", required = true, dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "字典标识", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "key", value = "字典key", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "字典名称", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "字典值", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "字典描述", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "color", value = "字典颜色", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "字典类型", required = false, dataTypeClass = SysDictTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "字典状态", required = false, dataTypeClass = StatusEnum.class, paramType = "query"),
    })
    @GetMapping
    public IPage<SysDict> getSysDictPage(@RequestParam("pageIndex") long pageIndex,
                                         @RequestParam("pageNumber") long pageNumber,
                                         @RequestParam(name = "code", required = false) String code,
                                         @RequestParam(name = "key", required = false) String key,
                                         @RequestParam(name = "name", required = false) String name,
                                         @RequestParam(name = "value", required = false) String value,
                                         @RequestParam(name = "remark", required = false) String remark,
                                         @RequestParam(name = "color", required = false) String color,
                                         @RequestParam(name = "type", required = false) SysDictTypeEnum sysDictTypeEnum,
                                         @RequestParam(name = "status", required = false) StatusEnum statusEnum
    ) {
        return sysDictService.getSysDictPage(pageIndex, pageNumber, code, key, name, value, remark, color, sysDictTypeEnum, statusEnum);
    }

    @ApiOperation(value = "获取启用的系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
    })
    @GetMapping("/all/enabled")
    public List<SysDict> getEnabledSysDictList() {
        return sysDictService.getEnabledSysDictList();
    }

    @ApiOperation(value = "获取系统字典集合", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{code:.*}")
    public List<SysDict> getSysDictList(@PathVariable(name = "code") String code) {
        return sysDictService.getSysDictList(code);
    }

    @ApiOperation(value = "获取系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "字典KEY", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{code:.*}/{key}")
    public SysDict getSysDict(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key) {
        return sysDictService.getSysDict(code, key);
    }

    @ApiOperation(value = "获取系统字典集合", notes = "仅针对alain使用", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/alain/tag/{code:.*}")
    public Map<Object, AlainTagOutputDTO> getSysDictByAlainTag(@PathVariable(name = "code") String code) {
        List<SysDict> sysDictList = sysDictService.getSysDictList(code);
        if (CollectionUtils.isNotEmpty(sysDictList)) {
            return sysDictList.stream()
                    .collect(Collectors.toMap((sysDict) -> {
                        if (sysDict.getType() == SysDictTypeEnum.STRING) {
                            return sysDict.getKey();
                        } else if (sysDict.getType() == SysDictTypeEnum.BYTE) {
                            return Byte.valueOf(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.SHORT) {
                            return Short.valueOf(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.INTEGER) {
                            return Integer.valueOf(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.LONG) {
                            return Long.valueOf(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.FLOAT) {
                            return Float.valueOf(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.DOUBLE) {
                            return Double.valueOf(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.BOOLEAN) {
                            return Boolean.valueOf(sysDict.getKey());
                        } else {
                            return sysDict.getKey();
                        }
                    }, (sysDict) -> new AlainTagOutputDTO(sysDict.getValue(), sysDict.getColor())));

        }
        return Collections.emptyMap();
    }


    @ApiOperation(value = "获取系统字典集合", notes = "仅针对alain使用", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/alain/select/{code:.*}")
    public List<AlainSelectOutputDTO> getSysDictByAlainSelect(@PathVariable(name = "code") String code) {
        List<SysDict> sysDictList = sysDictService.getSysDictList(code);
        if (CollectionUtils.isNotEmpty(sysDictList)) {
            return sysDictList.stream()
                    .map((sysDict -> {
                        if (sysDict.getType() == SysDictTypeEnum.STRING) {
                            return new AlainSelectOutputDTO(sysDict.getValue(), sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.BYTE) {
                            return new AlainSelectOutputDTO(sysDict.getValue(), Byte.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == SysDictTypeEnum.SHORT) {
                            return new AlainSelectOutputDTO(sysDict.getValue(), Short.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == SysDictTypeEnum.INTEGER) {
                            return new AlainSelectOutputDTO(sysDict.getValue(), Integer.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == SysDictTypeEnum.LONG) {
                            return new AlainSelectOutputDTO(sysDict.getValue(), Long.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == SysDictTypeEnum.FLOAT) {
                            return new AlainSelectOutputDTO(sysDict.getValue(), Float.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == SysDictTypeEnum.DOUBLE) {
                            return new AlainSelectOutputDTO(sysDict.getValue(), Double.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == SysDictTypeEnum.BOOLEAN) {
                            return new AlainSelectOutputDTO(sysDict.getValue(), Boolean.valueOf(sysDict.getKey()));
                        } else {
                            return new AlainSelectOutputDTO(sysDict.getValue(), sysDict.getKey());
                        }
                    }))
                    .collect(Collectors.toList());

        }
        return Collections.emptyList();
    }


    @ApiOperation(value = "创建系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "标识", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "key", value = "key", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "color", value = "颜色", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataTypeClass = SysDictTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PostMapping
    public void createSysDict(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "key") String key,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "type") SysDictTypeEnum type,
            @RequestParam(name = "order", required = false) Integer order,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysRoleId = Securitys.getSysUserId();
        sysDictService.createSysDict(code, key, name, value, remark, color, type, order, status, operatedSysRoleId);
    }

    @ApiOperation(value = "创建系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysDictInputDTO", value = "系统字典", required = true, dataTypeClass = SysDictInputDTO.class, paramType = "body"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void createSysDict(@RequestBody() SysDictInputDTO sysDictInputDTO) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysDictService.createSysDict(sysDictInputDTO.getCode(), sysDictInputDTO.getKey(), sysDictInputDTO.getName(), sysDictInputDTO.getValue(),
                sysDictInputDTO.getRemark(), sysDictInputDTO.getColor(), sysDictInputDTO.getType(), sysDictInputDTO.getOrder(), sysDictInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "修改系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "key", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "color", value = "颜色", required = false, dataTypeClass = String.class, paramType = "from"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataTypeClass = SysDictTypeEnum.class, paramType = "from"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataTypeClass = StatusEnum.class, paramType = "from"),
    })
    @PutMapping("/{code:.*}/{key}")
    public void updateSysDict(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "type") SysDictTypeEnum type,
            @RequestParam(name = "order", required = false) Integer order,
            @RequestParam(name = "status") StatusEnum status) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysDictService.updateSysDict(code, key, name, value, remark, color, type, order, status, operatedSysUserId);
    }

    @ApiOperation(value = "修改系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "key", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "SysDictInputDTO", value = "系统字典DTO", required = true, dataTypeClass = SysDictInputDTO.class, paramType = "body"),
    })
    @PutMapping(path = "/{code:.*}/{key}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void updateSysDict(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key,
            @RequestBody() SysDictInputDTO sysDictInputDTO) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysDictService.updateSysDict(code, key, sysDictInputDTO.getName(), sysDictInputDTO.getValue(), sysDictInputDTO.getRemark(),
                sysDictInputDTO.getColor(), sysDictInputDTO.getType(), sysDictInputDTO.getOrder(), sysDictInputDTO.getStatus(), operatedSysUserId);
    }

    @ApiOperation(value = "删除系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{code:.*}")
    public void deleteSysDict(
            @PathVariable(name = "code") String code) {
        sysDictService.deleteSysDict(code);
    }

    @ApiOperation(value = "删除系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "KEY", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @DeleteMapping("/{code:.*}/{key}")
    public void deleteSysDict(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key) {
        sysDictService.deleteSysDict(code, key);
    }

    @ApiOperation(value = "启用系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "key", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{code:.*}/{key}/enable")
    public void enabledSysDict(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysDictService.enabledSysDict(code, key, operatedSysUserId);
    }

    @ApiOperation(value = "禁用系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "key", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @PostMapping("/{code:.*}/{key}/disable")
    public void disableSysDict(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "key") String key) {
        String operatedSysUserId = Securitys.getSysUserId();
        sysDictService.disableSysDict(code, key, operatedSysUserId);
    }
}

