package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统权限API表
 * </p>
 *
 * @author chen
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_PERMISSION_API")
@ApiModel(value="SysPermissionApi对象", description="系统权限API表")
public class SysPermissionApi extends Model<SysPermissionApi> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统权限id")
    @TableId(value = "SYS_PERMISSION_ID", type = IdType.ID_WORKER_STR)
    private String sysPermissionId;

    @ApiModelProperty(value = "系统APIid")
    @TableField("SYS_API_ID")
    private String sysApiId;

    @ApiModelProperty(value = "创建系统用户id('为初始化创建)")
    @TableField("CREATE_SYS_USER_ID")
    private String createSysUserId;

    @TableField("CREATE_DATE_TIME")
    private LocalDateTime createDateTime;


    @Override
    protected Serializable pkVal() {
        return this.sysPermissionId;
    }

}
