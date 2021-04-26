package com.jiangwei.springboottest.myboot.redis;

import com.google.common.collect.Lists;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import com.jiangwei.springboottest.myboot.domains.Student;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: weijiang
 * @date: 2021/2/27
 * @description:
 **/
public class RedissonMapTest extends MybootApplicationTests {

    @Resource(name = "redisson")
    RedissonClient redissonClient;

    @Resource
    Jedis jedis;


    @Test
    public void testJedis() {
        String key = "mytestKey";
        jedis.set(key, "aaaaaa");

    }

    @Test
    public void testMapCache() {
        String mapKey = "myMap";
        String fieldKey = "std_";
        RMapCache<String, List<Student>> rMapCache = redissonClient.getMapCache(mapKey);
        for(int i=0; i<100000000; i++) {
            Student std = new Student();
            std.setId(i);
            List<Student> list = Lists.newArrayList(std);
            rMapCache.put(fieldKey+i, list);
        }

        System.out.println("sssssssssss");
    }


    @Test
    public void testSet() {
        String key = "mytest_";
        RBucket<Integer> rBucket = redissonClient.getBucket(key);
        rBucket.trySet(20, 600, TimeUnit.SECONDS);

        System.out.println("sssssssssss");
    }

    @Test
    public void testRmap() {
        String key = "myMap_TTL";
        /*RMap<Long, Integer> rMap = redissonClient.getMap(key);
        rMap.put(123L, 11);
        rMap.expire(15L, TimeUnit.MINUTES);*/

        RMapCache<Long, Integer> rMapCache = redissonClient.getMapCache(key);
        rMapCache.put(123L, 1122, 15L, TimeUnit.MINUTES);
        System.out.println("ok");
    }

    @Test
    public void testRmap1() {
        String key = "myMap_TTT";
        RMap<Long, Integer> rMap = redissonClient.getMap(key);
        rMap.put(123L, 11);
        rMap.expire(15L, TimeUnit.MINUTES);
        System.out.println("ok");
    }
}
