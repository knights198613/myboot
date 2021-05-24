package com.jiangwei.springboottest.myboot.redis;

import com.alibaba.fastjson.JSON;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import org.junit.Test;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: weijiang
 * @date: 2021/5/18
 * @description:
 **/
public class RedissonMapCacheTest extends MybootApplicationTests {


    @Resource(name = "redisson")
    RedissonClient redissonClient;


    long k1 = 123L;
    long k2 = 345L;
    long k3 = 456L;

    @Test
    public void testRmapCache() {
        String key = "msg_saleable_4009978";
        RMapCache<Long, Integer> rMapCache = redissonClient.getMapCache(key);

        rMapCache.put(k1, new Integer(11), 600L, TimeUnit.SECONDS);
        rMapCache.put(k2, new Integer(12), 600L, TimeUnit.SECONDS);
        rMapCache.put(k3, new Integer(13), 600L, TimeUnit.SECONDS);
    }

    @Test
    public void testRmapCacheReadAllMap() {
        String key = "msg_saleable_4009978";
        RMapCache<Long, Integer> rMapCache = redissonClient.getMapCache(key);
        Map<Long, Integer> myMap = rMapCache.readAllMap();
        System.out.println("myMap="+ JSON.toJSONString(myMap));
    }


    @Test
    public void testRmapCacheRemove() {
        String key = "msg_saleable_4009978";
        RMapCache<String, Integer> rMap = redissonClient.getMapCache(key);
        rMap.remove(k1);
        rMap.remove(k2);
        rMap.remove(k3);
    }
}
