package com.love.low.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * @name: Company
 * @description: 公司信息表
 * @author: BrownSugar
 * @date: 2024-04-02 08:23:18
 * @version: 1.0.0
 * @see BaseEntity
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("company")
public class Company extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 公司名称
     */
    @TableField("name")
    private String name;

    /**
     * 统一社会信用代码
     */
    @TableField("code")
    private String code;

    /**
     * 成立时间
     */
    @TableField("founding_time")
    private Date foundingTime;

    /**
     * 法人代表
     */
    @TableField("corporate_rep")
    private String corporateRep;

}
