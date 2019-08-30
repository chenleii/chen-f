package com.chen.f.common.web.controller;


import com.chen.f.common.pojo.SysDict;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/chen/common/sys/dict")
public class SysDictController {
    protected static final Logger logger = LoggerFactory.getLogger(SysDictController.class);

    @Autowired
    private ISysDictService sysDictService;

    @ApiOperation(value = "获取系统字典集合", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{code}/byCode")
    public List<SysDict> getSysDictList(@PathVariable(name = "code") String code) {
        return sysDictService.getSysDictList(code);
    }


    @ApiOperation(value = "获取系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictId", value = "系统字典ID", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{sysDictId}")
    public SysDict getSysDict(
            @PathVariable(name = "sysDictId") String sysDictId) {
        return sysDictService.getSysDict(sysDictId);
    }

    @ApiOperation(value = "获取系统字典", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典标识", required = true, dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "key", value = "字典KEY", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{code:.*}/{key}/byCodeAndKey")
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
        List<com.chen.f.common.pojo.SysDict> sysDictList = sysDictService.getSysDictList(code);
        if (CollectionUtils.isNotEmpty(sysDictList)) {
            return sysDictList.stream()
                    .collect(Collectors.toMap((sysDict) -> {
                        if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.STRING) {
                            return sysDict.getKey();
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.BYTE) {
                            return Byte.parseByte(sysDict.getKey());
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.SHORT) {
                            return Short.parseShort(sysDict.getKey());
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.INTEGER) {
                            return Integer.parseInt(sysDict.getKey());
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.LONG) {
                            return Long.parseLong(sysDict.getKey());
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.FLOAT) {
                            return Float.parseFloat(sysDict.getKey());
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.DOUBLE) {
                            return Double.parseDouble(sysDict.getKey());
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.BOOLEAN) {
                            return Boolean.parseBoolean(sysDict.getKey());
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
    public List<com.chen.f.common.web.dto.AlainSelectOutputDTO> getSysDictByAlainSelect(@PathVariable(name = "code") String code) {
        List<SysDict> sysDictList = sysDictService.getSysDictList(code);
        if (CollectionUtils.isNotEmpty(sysDictList)) {
            return sysDictList.stream()
                    .map((sysDict -> {
                        if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.STRING) {
                            return new com.chen.f.common.web.dto.AlainSelectOutputDTO(sysDict.getValue(), sysDict.getKey());
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.BYTE) {
                            return new com.chen.f.common.web.dto.AlainSelectOutputDTO(sysDict.getValue(), Byte.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.SHORT) {
                            return new com.chen.f.common.web.dto.AlainSelectOutputDTO(sysDict.getValue(), Short.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.INTEGER) {
                            return new com.chen.f.common.web.dto.AlainSelectOutputDTO(sysDict.getValue(), Integer.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.LONG) {
                            return new com.chen.f.common.web.dto.AlainSelectOutputDTO(sysDict.getValue(),Long.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.FLOAT) {
                            return new com.chen.f.common.web.dto.AlainSelectOutputDTO(sysDict.getValue(), Float.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == com.chen.f.common.pojo.enums.SysDictTypeEnum.DOUBLE) {
                            return new com.chen.f.common.web.dto.AlainSelectOutputDTO(sysDict.getValue(), Double.valueOf(sysDict.getKey()));
                        } else if (sysDict.getType() == SysDictTypeEnum.BOOLEAN) {
                            return new com.chen.f.common.web.dto.AlainSelectOutputDTO(sysDict.getValue(), Boolean.valueOf(sysDict.getKey()));
                        } else {
                            return new AlainSelectOutputDTO(sysDict.getValue(), sysDict.getKey());
                        }
                    }))
                    .collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

}

