package com.love.low;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @name: serviceApplication
 * @description: Service应用启动入口
 * @author: BrownSugar
 * @date: 2024-03-25 04:19:15
 * @version: 1.0.0
 **/
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
public class serviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(serviceApplication.class, args);
    }

}
