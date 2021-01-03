package com.jiangwei.springboottest.myboot.config.redis;

import com.jiangwei.springboottest.myboot.config.properties.RedisConfig;
import org.redisson.Redisson;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wuma (wuma@helijia.com)
 * @createDate: 2020/5/11
 * @company: (C) Copyright WWW.HELIJIA.COM 2020
 * @since: JDK 1.8
 * @description: 创建 redisson 客户端服务类
 */
@Configuration
public class RedisBean {


    @Bean(name = "redisson")
    public Redisson build(@Qualifier("redisConfig") RedisConfig redisConfig) {
        Config config = new Config();
        //config.setCodec(new StringCodec());
        config
                .useSingleServer()
                .setAddress(String.format("redis://%s", redisConfig.getAddress()))
                .setIdleConnectionTimeout(redisConfig.getIdleConnectionTimeout())
                .setTimeout(redisConfig.getTimeout())
                .setConnectTimeout(redisConfig.getConnectTimeout())
                .setRetryAttempts(redisConfig.getRetryAttempts())
                .setRetryInterval(redisConfig.getRetryInterval())
                .setConnectionMinimumIdleSize(redisConfig.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(redisConfig.getConnectionPoolSize());
        Redisson redisson = (Redisson) Redisson.create(config);
        return redisson;
    }
}
