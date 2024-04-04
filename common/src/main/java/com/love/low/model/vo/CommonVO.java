package com.love.low.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Name: BaseVO
 * @Description: 公共VO
 * @Author: BrownSugar
 * @Date: 2022-07-27 12:12:43
 * @Version: 1.0.0
 **/
@Data
public class CommonVO implements Serializable {

    @ApiModelProperty(name = "id", value = "ID")
    private Integer id;

    @ApiModelProperty(name = "name", value = "名称")
    private String name;

}
