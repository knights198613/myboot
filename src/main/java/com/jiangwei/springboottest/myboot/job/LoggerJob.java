package com.jiangwei.springboottest.myboot.job;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jiangwei.springboottest.myboot.entity.ShopUserNoticeEntity;
import com.jiangwei.springboottest.myboot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author: weijiang
 * @date: 2021/4/26
 * @description:
 **/

@Slf4j
@Component
public class LoggerJob {

    //private static Logger logger = LogManager.getLogger(LoggerJob.class);

    @Scheduled(fixedDelay = 2000)
    public void loggerInfo() {
        doLogInfoMessage();
    }

    /**
     * 记录Info日志
     */
    private void doLogInfoMessage() {
        List<ShopUserNoticeEntity> shopUserNoticeEntities = Lists.newArrayList();
        for(int i=0; i<500; i++) {
            shopUserNoticeEntities.add(createEntity());
        }
        String curDateStr = TimeUtils.getCurrentDateString(null, new Date());
        for(int x=0; x<100; x++) {
            log.info("当前系统时间为={}, shopUserNoticeEntityList={}.", curDateStr, JSON.toJSONString(shopUserNoticeEntities));
        }
        shopUserNoticeEntities.clear();
    }

    /**
     * 记录错误日志JOB
     */
    @Scheduled(fixedDelay = 2000)
    public void loggerError() {
        doLogErrorMessage();
    }

    /**
     * 记录错误日志
     */
    public void doLogErrorMessage() {
        List<ShopUserNoticeEntity> shopUserNoticeEntities = Lists.newArrayList();
        for(int i=0; i<100; i++) {
            shopUserNoticeEntities.add(createEntity());
        }

        String curDateStr = TimeUtils.getCurrentDateString(null, new Date());
        for(int x=0; x<300; x++) {
            log.error("当前系统时间为={}, shopUserNoticeEntityList={}.", curDateStr, JSON.toJSONString(shopUserNoticeEntities));
        }
        shopUserNoticeEntities.clear();
    }

    private ShopUserNoticeEntity createEntity() {
        ShopUserNoticeEntity entity = new ShopUserNoticeEntity();
        entity.setActivityType(100);
        entity.setCreateTime(new Date());
        entity.setEndTime(TimeUtils.getCurrentDateString(null, new Date()));
        entity.setGoodsId(12345L);
        entity.setId(123L);
        entity.setJumpUrl("http://localhost:8099/index.jsp");
        entity.setMessageType(10);
        entity.setStatus(5);
        entity.setReplenishmentTime(new Date());
        entity.setUserId(999999L);
        entity.setParam("my param is OK");
        return entity;
    }




}
