package com.love.low.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @name: NetInspectRep
 * @description: 网络核查
 * @author: BrownSugar
 * @date: 2024-04-01 05:09:08
 * @version: 1.0.0
 * @see BaseEntity
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("inr_net_inspect_rep")
public class NetInspectRep extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 网站分类
     */
    @TableField("site_category")
    private String siteCategory;

    /**
     * 网站名称
     */
    @TableField("site_name")
    private String siteName;

    /**
     * 网站地址
     */
    @TableField("site_url")
    private String siteUrl;

}
