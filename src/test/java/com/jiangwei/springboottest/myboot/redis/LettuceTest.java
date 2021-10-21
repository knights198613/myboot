package com.jiangwei.springboottest.myboot.redis;

import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: weijiang
 * @date: 2021/10/20
 * @description:
 **/
public class LettuceTest extends MybootApplicationTests {

    @Resource
    RedisURI redisURI;

    @Test
    public void testInsertRedis() {
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> statefulRedisConnection = redisClient.connect();
        RedisCommands<String, String> redisCommands = statefulRedisConnection.sync();
        redisCommands.set("lettuce-test", "1234567890");
        statefulRedisConnection.close();
        redisClient.shutdown();
    }


    @Test
    public void testGetRedis() {
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> commands = connection.sync();
        String result = commands.get("lettuce-test");
        System.out.println("get by key lettuce-test:" + result);
        connection.close();
        redisClient.shutdown();
    }
}
