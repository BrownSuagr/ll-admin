package com.love.low.asynchronous;

import org.springframework.scheduling.annotation.Async;

/**
 * @name: AsyncService
 * @description: 异步任务服务接口
 * @author: BrownSugar
 * @date: 2023-09-18 10:46:15
 * @version: 1.0.0
 **/
public interface AsyncService {

    /**
     * @description: 标签保存-执行客户打标签异步操作
     * @param: [map, currentUserId]
     * @author: BrownSugar
     * @date: 2023-10-19 03:01:09
     **/
    @Async("taskExecutor")
    void executeNewsInsert(Integer currentUserId);
}