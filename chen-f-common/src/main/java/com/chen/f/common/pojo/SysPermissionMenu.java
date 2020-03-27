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
 * 系统权限菜单表
 * </p>
 *
 * @author chen
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission_menu")
@ApiModel(value="SysPermissionMenu对象", description="系统权限菜单表")
public class SysPermissionMenu extends Model<SysPermissionMenu> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
      @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "系统权限ID")
    @TableField("SYS_PERMISSION_ID")
    private String sysPermissionId;

    @ApiModelProperty(value = "系统菜单ID")
    @TableField("SYS_MENU_ID")
    private String sysMenuId;

    @ApiModelProperty(value = "创建系统用户ID(空白字符串为初始化创建)")
    @TableField("CREATE_SYS_USER_ID")
    private String createSysUserId;

    @TableField("CREATE_DATE_TIME")
    private LocalDateTime createDateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
