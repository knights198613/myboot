package com.jiangwei.springboottest.myboot.redis;

import com.google.common.collect.Lists;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import com.jiangwei.springboottest.myboot.domains.Student;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

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
}
