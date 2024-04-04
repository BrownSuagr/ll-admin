package com.love.low.encapsulation;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @enumName: ResultCodeEnum
 * @description:
 * @author: sh.Liu
 * @date: 2022-01-13 17:30
 */
public enum ResultCodeEnum implements Serializable {

    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    UNAUTHORIZED(401, "未认证（签名错误）"),
    FORBIDDEN(403, "禁止访问-------测试"),
    NOT_FOUND(404, "接口不存在"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!"),
    AUTH_ERROR(-10000, "鉴权登陆失败，请重新登录！"),


    BIZ_ERROR(1000, "通用业务异常"),

    PARAMETER_ERROR(1002,"请求参数有误或重复"),
    PARAMETER_MISSING_ERROR(1003,"确少必要请求参数!"),
    PARAMETER_NOT_VALID(1004,"方法参数无效"),

    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),
    FILE_OUT_MAX(9000, "文件超出最大限制"),
    FILE_FORMAT_ERROR(9001, "文件格式不正确"),
    PARAM_ERROR(9050, "参数错误"),
    JSON_FORMAT_ERROR(9051, "Json解析异常"),
    SQL_ERROR(9052, "Sql解析异常"),
    NETWORK_TIMEOUT(9510, "网络超时"),
    UNKNOWN_INTERFACE(9520, "未知的接口"),
    REQ_MODE_NOT_SUPPORTED(9530, "请求方式不支持"),
    SYSTEM_ERROR(9998,"系统异常!"),
    UNKNOWN_ERROR(9999,"未知的错误!");

    /**
     * 状态码
     */
    @Getter
    @Setter
    private Integer code;

    /**
     * 状态信息
     */
    @Getter
    @Setter
    private String message;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
