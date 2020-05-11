package com.jiangwei.springboottest.myboot.config.redis;

import com.helijia.framework.datasource.cfg.SimplePasswordCallback;
import com.helijia.framework.redis.AliyunRedisServiceImpl;
import com.jiangwei.springboottest.myboot.config.properties.RedisConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wuma (wuma@helijia.com)
 * @createDate: 2020/5/11
 * @company: (C) Copyright WWW.HELIJIA.COM 2020
 * @since: JDK 1.8
 * @description: 创建redis客户端服务类
 */
@Configuration
public class RedisBean {
    @Value("${app.name}")
    private String appName;
    @Value("${app.group}")
    private String appGroup;


    @Bean(name = "redisService", initMethod = "init", destroyMethod = "shutdown")
    public AliyunRedisServiceImpl build(@Qualifier("redisConfig") RedisConfig redisConfig) {
        AliyunRedisServiceImpl redisService = new AliyunRedisServiceImpl();
        redisService.setAppName(appName);
        redisService.setNamespace(appGroup);
        redisService.setHost(redisConfig.getHost());
        redisService.setPort(Integer.valueOf(redisConfig.getPort()));
        SimplePasswordCallback simplePasswordCallback = new SimplePasswordCallback();
        simplePasswordCallback.setPassword(redisConfig.getPassword());
        redisService.setPassword(simplePasswordCallback.getPassword());
        return redisService;
    }
}
