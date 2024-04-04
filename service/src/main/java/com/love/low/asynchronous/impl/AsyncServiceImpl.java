package com.love.low.asynchronous.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bs.asynchronous.AsyncService;
import com.bs.entity.NewsRanking;
import com.bs.mapper.NewsRankingMapper;
import com.bs.model.vo.NewsListVO;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.bs.enums.DataStatusEnum.VALID;

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
    final NewsRankingMapper newsRankingMapper;

    /**
     * @description: 执行保存热点新闻
     * @param: [map, currentUserId]
     * @author: BrownSugar
     * @date: 2023-11-02 06:18:56
     **/
    @Override
    public void executeNewsInsert(Map<Integer, List<NewsListVO>> map, Integer currentUserId) {
        LOG.info("Interface：定时任务-打标签异步操作 MethodName：executeCustomerMarking Params：map: {} currentUserId: {}", map, currentUserId);
        if(CollUtil.isEmpty(map)){
            return;
        }
        List<NewsRanking> insertList = Lists.newArrayList();
        map.forEach((k,v) ->{
            if(CollUtil.isNotEmpty(v)){
                v.forEach(n ->{
                    NewsRanking newsRanking = new NewsRanking();
                    BeanUtil.copyProperties(n, newsRanking);
                    newsRanking.setSourceId(k);
                    insertList.add(newsRanking);

                    LambdaQueryWrapper<NewsRanking> qw = new LambdaQueryWrapper<>();
                    qw.eq(NewsRanking::getTitle, newsRanking.getTitle())
                      .eq(NewsRanking::getSourceId, newsRanking.getSourceId())
                      .eq(NewsRanking::getCurrentDate, DateUtil.date().toSqlDate())
                      .eq(NewsRanking::getDataStatus, VALID.getCode());
                    boolean exists = newsRankingMapper.exists(qw);
                    if(!exists){
                        newsRankingMapper.insert(newsRanking);
                    }
                });
            }
        });
        //newsRankingMapper.insertOrUpdateBatch(insertList);
    }

}
