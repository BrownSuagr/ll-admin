package com.love.low.enmus;

import lombok.Getter;

/**
 * @Name: DataStatusEnum
 * @Description: 实体类的obj_status属性枚举
 * @Author: BrownSugar
 * @Date: 2022-04-05 03:56:52
 * @Version: 1.0.0
 * @see Enum
 **/
public enum DataStatusEnum {

    VALID("Y", "有效"),
    INVALID("N", "无效");

    @Getter
    private String code;
    @Getter
    private String desc;

    DataStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
