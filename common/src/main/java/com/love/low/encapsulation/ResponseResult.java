package com.love.low.encapsulation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @Name: ResponseResult
 * @Description: 自定义统一响应体返回消息类
 * @Author: BrownSugar
 * @Date: 2023-06-11 02:55:40
 * @Version: 1.0.0
 * @see Serializable
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseResult.class);
    /**
     * 返回状态码
     */
    private int code;
    /**
     * 状态消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;


    /**
     * @Description:
     * @Param: [code, message]
     * @Return:
     * @Author: BrownSugar
     * @Date: 2023-06-10 10:29:24
     **/
    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
        LOGGER.info(toString());
    }
    


    public static ResponseResult success() {
        return new ResponseResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), true);
    }

    public static <T> ResponseResult<T> success(int code, T t) {
        return new ResponseResult(code, "", t);
    }

    public static <T> ResponseResult<T> success(int code, String message, T t) {
        return new ResponseResult(code, message, t);
    }

    public static <T> ResponseResult<T> success(T t) {
        return new ResponseResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), t);
    }

    public static ResponseResult error() {
        return error("");
    }

    public static ResponseResult error(String message) {
        return error(ResultCodeEnum.SYSTEM_INNER_ERROR.getCode(), message);
    }

    public static ResponseResult error(int code, String message) {
        return error(code, message, null);
    }

    public static <T> ResponseResult<T> error(int code, String message, T t) {
        return new ResponseResult(code, message, t);
    }


}