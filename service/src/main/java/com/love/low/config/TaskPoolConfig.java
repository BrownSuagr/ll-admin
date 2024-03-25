package com.love.low.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @name: TaskPoolConfig
 * @description: 线程池配置
 * @author: BrownSugar
 * @date: 2023-09-18 10:33:48
 * @version: 1.0.0
 **/
@EnableAsync
@Configuration
public class TaskPoolConfig {

    private static final Logger LOG = LoggerFactory.getLogger(TaskPoolConfig.class);

    /**
     * @return {@code Executor }
     * @description: 自定义线程池
     * @param: []
     * @author: BrownSugar
     * @date: 2023-09-18 10:34:03
     **/
    @Bean("taskExecutor")
    public Executor taskExecutor() {
        LOG.info("start ThreadPoolExecutor");
        // CPU密集型线程池，池大小：CPU核心数+1
        int threadNum = Runtime.getRuntime().availableProcessors() + 1;
        LOG.info("系统最大线程数:{}", threadNum);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(threadNum);
        //最大线程数
        executor.setMaxPoolSize(threadNum);
        //配置队列容量，默认值为Integer.MAX_VALUE
        executor.setQueueCapacity(99999);
        //活跃时间
        executor.setKeepAliveSeconds(60);
        //线程名字前缀
        executor.setThreadNamePrefix("asyncServiceExecutor-");
        //设置此执行程序应该在关闭时阻止的最大秒数，以便在容器的其余部分继续关闭之前等待剩余的任务完成他们的执行
        executor.setAwaitTerminationSeconds(60);
        //等待所有的任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}