package com.jiangwei.springboottest.myboot.job;

import com.jiangwei.springboottest.myboot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: weijiang
 * @date: 2021/4/26
 * @description:
 **/

@Slf4j
@Component
public class LoggerJob {


    @Scheduled(fixedDelay = 1000)
    public void loggerInfo() {
         String curDateStr = TimeUtils.getCurrentDateString(null, new Date());
        log.info("当前系统时间为："+curDateStr);
    }

}
