package com.love.low.encapsulation;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Name: GlobalResponseBodyAdvice
 * @Description:
 * @Author: BrownSugar
 * @Date: 2023-06-09 11:51:45
 * @Version: 1.0.0
 * @see ResponseBodyAdvice
 **/
@RestControllerAdvice
@Slf4j
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getDeclaringClass().isAnnotationPresent(ResponseNotIntercept.class)) {
            //若在类中加了@ResponseNotIntercept 则该类中的方法不用做统一的拦截
            return false;
        }
        if (returnType.getMethod().isAnnotationPresent(ResponseNotIntercept.class)) {
            //若方法上加了@ResponseNotIntercept 则该方法不用做统一的拦截
            return false;
        }
        if(returnType.getDeclaringClass().getName().contains("springfox")){
            return false;
        }
        return true;
    }


    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        //log.warn("body:{}", body);
        if (body instanceof String) {
            //log.warn("String-body:{}", body);
            //解决返回值为字符串时，不能正常包装
            return ResponseResult.success(body);
        }

        if (body instanceof ResponseResult) {
            // 提供一定的灵活度，如果body已经被包装了，就不进行包装
            return body;
        }

        //log.warn("Res-body:{}", body);
        return ResponseResult.success(body);
    }
}
