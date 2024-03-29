package com.jiangwei.springboottest.myboot.config.redis;

import com.jiangwei.springboottest.myboot.config.properties.RedisConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;

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
        config.useSingleServer()
                .setAddress(String.format("redis://%s", redisConfig.getAddress()))
                .setIdleConnectionTimeout(redisConfig.getIdleConnectionTimeout())
                .setTimeout(redisConfig.getTimeout())
                .setConnectTimeout(redisConfig.getConnectTimeout())
                .setRetryAttempts(redisConfig.getRetryAttempts())
                .setRetryInterval(redisConfig.getRetryInterval())
                .setConnectionMinimumIdleSize(redisConfig.getConnectionMinimumIdleSize())
                .setPassword(redisConfig.getPassword())
                .setConnectionPoolSize(redisConfig.getConnectionPoolSize());
        Redisson redisson = (Redisson) Redisson.create(config);
        return redisson;
    }


    @Bean(name = "jedisClient")
    public Jedis createJedis(@Qualifier("redisConfig") RedisConfig redisConfig) {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(redisConfig.getConnectionPoolSize());
        genericObjectPoolConfig.setMinIdle(redisConfig.getConnectionMinimumIdleSize());
        String[] hostAndPort = redisConfig.getAddress().split(":");
        JedisPool jedisPool = new JedisPool(genericObjectPoolConfig,
                hostAndPort[0],
                Integer.valueOf(hostAndPort[1]),
                redisConfig.getConnectTimeout(),
                redisConfig.getPassword());
        return jedisPool.getResource();
    }

    @Bean(name="redisURI")
    public RedisURI createReidsURI(@Qualifier("redisConfig") RedisConfig redisConfig) {
        String[] hostAndPort = redisConfig.getAddress().split(":");
        RedisURI redisURI = RedisURI.builder()
                .withHost(hostAndPort[0])
                .withPort(Integer.valueOf(hostAndPort[1]))
                .withPassword(redisConfig.getPassword().toCharArray())
                .withTimeout(Duration.ofMillis(redisConfig.getConnectTimeout()))
                .withDatabase(0)
                .build();
        return redisURI;
    }

    @Bean(name = "redisClient", destroyMethod ="shutdown" )
    public RedisClient createRedisClient(RedisURI redisURI) {
        RedisClient redisClient = RedisClient.create(redisURI);
        return redisClient;
    }

    @Bean(name = "statefulRedisConnection", destroyMethod = "close")
    public StatefulRedisConnection<String, String> createRedisConnection(RedisClient redisClient) {
        return redisClient.connect();
    }
}
