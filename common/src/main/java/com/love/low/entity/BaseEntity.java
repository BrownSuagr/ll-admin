package com.love.low.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.love.low.enmus.DataStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Name: BaseEntity
 * @Description: 实体类父类
 * @Author: BrownSugar
 * @Date: 2022-04-05 06:53:32
 * @Version: 1.0.0
 * @see Serializable
 **/

@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    /**
     * 数据状态
     **/
    @TableField("valid_flag")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private String validFlag = DataStatusEnum.VALID.getCode();

    /**
     * 创建日期
     **/
    @TableField(value = "date_created", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private Date dateCreated;

    /**
     * 创建人
     **/
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private Integer createdBy;

    /**
     *修改日期
     **/
    @TableField(value = "date_updated", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private Date dateUpdated;

    /**
     * 修改人
     **/
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private Integer updatedBy;

}