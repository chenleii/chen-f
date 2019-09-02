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
 * 国家表
 * </p>
 *
 * @author chen
 * @since 2018-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("COUNTRY")
@ApiModel(value="Country对象", description="国家表")
public class Country extends Model<Country> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "二位字母代码")
    @TableId(value = "LETTER_CODE2")
    private String letterCode2;

    @ApiModelProperty(value = "三位字母代码")
    @TableField("LETTER_CODE3")
    private String letterCode3;

    @ApiModelProperty(value = "英文名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "中文名称")
    @TableField("CHINESE_NAME")
    private String chineseName;

    @ApiModelProperty(value = "三位数字代码")
    @TableField("NUMERIC_CODE")
    private String numericCode;

    @ApiModelProperty(value = "国际电话区号")
    @TableField("AREA_CODE")
    private String areaCode;


    @Override
    protected Serializable pkVal() {
        return this.letterCode2;
    }

}
