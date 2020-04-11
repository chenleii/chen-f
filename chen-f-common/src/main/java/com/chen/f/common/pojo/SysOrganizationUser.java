package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统组织用户表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_organization_user`")
@ApiModel(value="SysOrganizationUser对象", description="系统组织用户表")
public class SysOrganizationUser extends Model<SysOrganizationUser> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统组织用户ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "系统组织ID")
    @TableField("`SYS_ORGANIZATION_ID`")
    private String sysOrganizationId;

    @ApiModelProperty(value = "系统用户ID")
    @TableField("`SYS_USER_ID`")
    private String sysUserId;

    @ApiModelProperty(value = "创建的系统用户ID(空白字符串为初始化创建)")
    @TableField("`CREATED_SYS_USER_ID`")
    private String createdSysUserId;

    @ApiModelProperty(value = "创建的日期时间")
    @TableField("`CREATED_DATE_TIME`")
    private LocalDateTime createdDateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
