package com.love.low.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

/**
 * @Name: SwaggerConfig
 * @Description: Swagger配置文件
 * @Author: BrownSugar
 * @Date: 2022-04-02 04:43:15
 * @Version: 1.0.0
 **/
@EnableSwagger2
@EnableKnife4j
@Configuration
public class SwaggerConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2)
                .enableUrlTemplating(false)
                .apiInfo(apiInfo())
                // 选择那些路径和api会生成document
                .select()
                // 对所有api进行监控
                .apis(withClassAnnotation(Api.class))
                //这里可以自定义过滤
                .paths(this::filterPath)
                ;
        return builder.build();
    }

    private boolean filterPath(String path) {
        boolean ret = path.endsWith("/error");
        if (ret) {
            return false;
        }
        //这块可以写其他的过滤逻辑
        return true;
    }

    /**
     * @Description: 接口的信息
     * @Param: []
     * @Return: ApiInfo
     * @Author: BrownSugar
     * @Date: 2022-04-02 04:39:17
     **/
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("私人项目对外业务")
                .description("博客、问答机器人类似对外业务模块")
                .termsOfServiceUrl("http://黑糖.site/")
                .version("1.0")
                .contact(new Contact("BS-Service", "http://黑糖.site", "heitanghei@gmail.com"))
                .build();
    }
}