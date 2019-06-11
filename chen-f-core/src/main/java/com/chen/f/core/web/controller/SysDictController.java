package com.chen.f.core.web.controller;


import com.chen.f.core.pojo.SysDict;
import com.chen.f.core.pojo.enums.SysDictTypeEnum;
import com.chen.f.core.service.ISysDictService;
import com.chen.f.core.web.dto.AlainSelectOutputDTO;
import com.chen.f.core.web.dto.AlainTagOutputDTO;
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
@RequestMapping("/chen/core/sys/dict")
public class SysDictController {
    protected static final Logger logger = LoggerFactory.getLogger(SysDictController.class);

    @Autowired
    private ISysDictService sysDictService;

    @ApiOperation(value = "获取系统字典集合", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典标识", required = true, dataTypeClass = String.class, paramType = "path"),
    })
    @GetMapping(path = "/{code}")
    public List<SysDict> getSysDict(@PathVariable(name = "code") String code) {
        return sysDictService.getSysDictList(code);
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
                            return Byte.parseByte(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.SHORT) {
                            return Short.parseShort(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.INTEGER) {
                            return Integer.parseInt(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.LONG) {
                            return Long.parseLong(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.FLOAT) {
                            return Float.parseFloat(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.DOUBLE) {
                            return Double.parseDouble(sysDict.getKey());
                        } else if (sysDict.getType() == SysDictTypeEnum.BOOLEAN) {
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
                            return new AlainSelectOutputDTO(sysDict.getValue(),Long.valueOf(sysDict.getKey()));
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

}

