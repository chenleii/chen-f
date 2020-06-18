package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.common.pojo.enums.SysApiTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统接口表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_api`")
@ApiModel(value="SysApi对象", description="系统接口表")
public class SysApi extends Model<SysApi> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统接口ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "系统接口名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "系统接口URL")
    @TableField("`URL`")
    private String url;

    @ApiModelProperty(value = "系统接口HTTP请求方法(GET:GET请求;HEAD:HEAD请求;POST:POST请求;PUT:PUT请求;PATCH:PATCH请求;DELETE:DELETE请求;OPTIONS:OPTIONS请求;TRACE:TRACE请求;ANY:任意的请求;)")
    @TableField("`HTTP_METHOD`")
    private SysApiHttpMethodEnum httpMethod;

    @ApiModelProperty(value = "系统接口类型(SYSTEM:系统接口;LOGIN:登陆接口;LOGOUT:登出接口;)")
    @TableField("`TYPE`")
    private SysApiTypeEnum type;

    @ApiModelProperty(value = "系统接口备注")
    @TableField("`REMARK`")
    private String remark;

    @ApiModelProperty(value = "系统接口状态(ENABLED:启用;DISABLED:禁用;)")
    @TableField("`STATUS`")
    private StatusEnum status;

    @ApiModelProperty(value = "更新的系统用户ID(空白字符串为初始化创建)")
    @TableField("`UPDATED_SYS_USER_ID`")
    private String updatedSysUserId;

    @ApiModelProperty(value = "创建的系统用户ID(空白字符串为初始化创建)")
    @TableField("`CREATED_SYS_USER_ID`")
    private String createdSysUserId;

    @ApiModelProperty(value = "更新的日期时间")
    @TableField("`UPDATED_DATE_TIME`")
    private LocalDateTime updatedDateTime;

    @ApiModelProperty(value = "创建的日期时间")
    @TableField("`CREATED_DATE_TIME`")
    private LocalDateTime createdDateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
