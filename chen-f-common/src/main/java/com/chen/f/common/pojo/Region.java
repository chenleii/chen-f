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

/**
 * <p>
 * 地区表
 * </p>
 *
 * @author chen
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`region`")
@ApiModel(value="Region对象", description="地区表")
public class Region extends Model<Region> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "地区ID")
      @TableId(value = "`ID`", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "父级的地区ID(引用本表ID字段,空白字符串为顶级)")
    @TableField("`PARENT_ID`")
    private String parentId;

    @ApiModelProperty(value = "地区等级(0:国家;1:省;2:市;3:区/县;)")
    @TableField("`LEVEL`")
    private Integer level;

    @ApiModelProperty(value = "地区名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "地区城市编码")
    @TableField("`CITY_CODE`")
    private String cityCode;

    @ApiModelProperty(value = "地区邮政编码")
    @TableField("`POSTAL_CODE`")
    private String postalCode;

    @ApiModelProperty(value = "地区简称")
    @TableField("`SHORT_NAME`")
    private String shortName;

    @ApiModelProperty(value = "地区全称")
    @TableField("`FULL_NAME`")
    private String fullName;

    @ApiModelProperty(value = "地区英文名称")
    @TableField("`ENGLISH_NAME`")
    private String englishName;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
