package com.jiangwei.springboottest.myboot.redis;

import com.google.common.collect.Lists;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import com.jiangwei.springboottest.myboot.domains.Page;
import com.jiangwei.springboottest.myboot.domains.Student;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Autowired
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
    public void testRedssionSet() {
        String key = "mytest_page";
        RBucket<Page<Student>> rBucket = redissonClient.getBucket(key);

        Page<Student> pages = new Page<>();

        List<Student> stdList = new ArrayList<>();
        Student st1 = new Student();
        st1.setId(1);
        st1.setName("Jhon smith");
        st1.setFrom("LanZhou");
        st1.setOrder(40);
        st1.setSex("男");
        stdList.add(st1);

        Student st2 = new Student();
        st2.setId(2);
        st2.setName("Tom walter");
        st2.setFrom("Xian");
        st2.setOrder(60);
        stdList.add(st2);

        pages.setData(stdList);
        pages.setPageNum(1);
        pages.setPageSize(10);
        pages.setStatus(true);
        pages.setTotalNum(2);

        rBucket.set(pages);

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
