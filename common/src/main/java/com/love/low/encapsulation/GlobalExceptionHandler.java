package com.love.low.encapsulation;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Name: GlobalExceptionHandler
 * @Description: 统一拦截异常
 * @Author: BrownSugar
 * @Date: 2023-06-11 11:54:04
 * @Version: 1.0.0
 **/

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Description: 参数不正确
     * @Param: [req, ex]
     * @Return: CommonResult
     * @Author: BrownSugar
     * @Date: 2023-06-11 12:01:10
     **/
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseResult missingServletRequestParameterExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e) {
        log.error("missingServletRequestParameterExceptionHandler message:{} exception:{}",e.getMessage(), e);
        // 包装 CommonResult 结果
        return ResponseResult.error(ResultCodeEnum.PARAMETER_ERROR.getCode(), ResultCodeEnum.PARAMETER_ERROR.getMessage());
    }

    /**
     * @Description:
     * @Param: [req, ex]
     * @Return: ResponseResult
     * @Author: BrownSugar
     * @Date: 2023-06-11 02:49:10
     **/
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseResult constraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
        log.error("constraintViolationExceptionHandler message:{} exception:{}",e.getMessage(), e);
        // 拼接错误
        StringBuilder detailMessage = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            // 拼接内容到其中
            detailMessage.append(constraintViolation.getMessage());
        }
        // 包装 CommonResult 结果
        return ResponseResult.error(ResultCodeEnum.PARAMETER_MISSING_ERROR.getCode(), ResultCodeEnum.PARAMETER_MISSING_ERROR.getMessage() + ":" + detailMessage.toString());
    }

    /**
     * @Description:
     * @Param: [req, ex]
     * @Return: ResponseResult
     * @Author: BrownSugar
     * @Date: 2023-06-11 12:18:27
     **/
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseResult bindExceptionHandler(HttpServletRequest req, BindException e) {
        log.error("bindExceptionHandler message:{} exception:{}",e.getMessage(), e);
        // 拼接错误
        StringBuilder detailMessage = new StringBuilder();
        for (ObjectError objectError : e.getAllErrors()) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            // 拼接内容到其中
            detailMessage.append(objectError.getDefaultMessage());
        }
        // 包装 CommonResult 结果
        return ResponseResult.error(ResultCodeEnum.PARAMETER_MISSING_ERROR.getCode(), ResultCodeEnum.PARAMETER_MISSING_ERROR.getMessage() + ":" + detailMessage.toString());
    }


    /**
     * @return {@code Object }
     * @description: 参数校验
     * @param: [request, e]
     * @author: BrownSugar
     * @date: 2023-12-04 06:32:21
     **/
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object errorHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidExceptionHandler message:{} exception:{}",e.getMessage(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        if (CollUtil.isEmpty(errors)) {
            return errors;
        }
        String message = errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        return ResponseResult.error(ResultCodeEnum.PARAMETER_ERROR.getCode(), message);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseResult authExceptionHandler(AuthException e) {
        log.error("AuthException异常" , e);
        log.error("AuthException异常信息：{}", e.getMessage());
        return ResponseResult.error(ResultCodeEnum.SERVER_BUSY.getCode(), ResultCodeEnum.SERVER_BUSY.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException异常" , e);
        log.error("RuntimeException异常信息：{}", e.getMessage());
        String message = e.getMessage();
        // 包装 CommonResult 结果
        return ResponseResult.error(ResultCodeEnum.BIZ_ERROR.getCode(), ResultCodeEnum.BIZ_ERROR.getMessage() + ": [" + message + "]");
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult exceptionHandler(Exception e) {
        log.error("Exception异常" , e);
        log.error("Exception异常信息：{}", e.getMessage());
        return ResponseResult.error(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult httpClientErrorHandler(Exception e) {
        log.error("Exception异常" , e);
        log.error("Exception异常信息：{}", e.getMessage());
        return ResponseResult.error(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseResult NoSuchMethodErrorHandler(Exception e) {
        log.error("Exception异常" , e);
        log.error("Exception异常信息：{}", e.getMessage());
        return ResponseResult.error(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

}

