package com.love.low.asynchronous.impl;

import com.love.low.asynchronous.AsyncService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @name: AsyncServiceImpl
 * @description: 异步任务服务实现类
 * @author: BrownSugar
 * @date: 2023-09-18 10:45:42
 * @version: 1.0.0
 * @see AsyncService
 **/
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AsyncServiceImpl implements AsyncService {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncServiceImpl.class);

    /**
     * @description: 执行保存热点新闻
     * @param: [map, currentUserId]
     * @author: BrownSugar
     * @date: 2023-11-02 06:18:56
     **/
    @Override
    public void executeNewsInsert(Integer currentUserId) {
        LOG.info("Interface：定时任务-打标签异步操作 MethodName：executeCustomerMarking Params：map: {} currentUserId: {}", currentUserId);
    }

}
