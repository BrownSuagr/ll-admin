package com.love.low.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class NetInspectRepDTO {
    private Integer id;
    // 核查对象
    private String subject;

    private String siteCategory;
    private String siteName;
    private String siteUrl;
    private String checkDate;
    private String resultNum;
}