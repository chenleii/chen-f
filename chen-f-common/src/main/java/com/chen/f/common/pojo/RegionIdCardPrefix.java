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
 * 地区身份证号前缀
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("REGION_ID_CARD_PREFIX")
@ApiModel(value="RegionIdCardPrefix对象", description="地区身份证号前缀")
public class RegionIdCardPrefix extends Model<RegionIdCardPrefix> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "身份证号前缀")
    @TableId(value = "PREFIX", type = IdType.ID_WORKER_STR)
    private String prefix;

    @ApiModelProperty(value = "全称")
    @TableField("PARENT_PREFIX")
    private String parentPrefix;

    @ApiModelProperty(value = "省前缀")
    @TableField("PROVINCE")
    private String province;

    @ApiModelProperty(value = "城市前缀")
    @TableField("CITY")
    private String city;

    @ApiModelProperty(value = "地区名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "全称")
    @TableField("FULL_NAME")
    private String fullName;


    @Override
    protected Serializable pkVal() {
        return this.prefix;
    }

}
