package com.chen.f.common.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 地区身份证表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`region_id_card`")
@ApiModel(value="RegionIdCard对象", description="地区身份证表")
public class RegionIdCard extends Model<RegionIdCard> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "地区身份证ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "父级的地区身份证ID(引用本表ID字段,空白字符串为顶级)")
    @TableField("`PARENT_ID`")
    private String parentId;

    @ApiModelProperty(value = "地区身份证号前缀")
    @TableField("`PREFIX`")
    private String prefix;

    @ApiModelProperty(value = "父级的地区身份证号前缀(引用本表PREFIX字段,空白字符串为顶级)")
    @TableField("`PARENT_PREFIX`")
    private String parentPrefix;

    @ApiModelProperty(value = "地区身份证省前缀")
    @TableField("`PROVINCE`")
    private String province;

    @ApiModelProperty(value = "地区身份证城市前缀")
    @TableField("`CITY`")
    private String city;

    @ApiModelProperty(value = "地区身份证地区名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "地区身份证地区全称")
    @TableField("`FULL_NAME`")
    private String fullName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
