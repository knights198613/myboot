package com.jiangwei.springboottest.myboot.redis;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import com.jiangwei.springboottest.myboot.config.redis.RedisProtocolBox;
import com.jiangwei.springboottest.myboot.domains.Page;
import com.jiangwei.springboottest.myboot.domains.Student;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: weijiang
 * @date: 2021/5/10
 * @description:
 **/
public class JedisAndRedissionTest extends MybootApplicationTests {


    @Resource(name = "redisson")
    RedissonClient redissonClient;

    @Resource(name = "jedisClient")
    private Jedis jedisClient;

    @Resource
    RedisProtocolBox redisProtocolBox;





    @Test
    public void testRedissionSetBucket() {
        String key = "proxy_mytest_page";


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

        RBucket<Page<Student>> rBucket = redissonClient.getBucket(key);
        rBucket.set(pages);

        Page<Student> page = rBucket.get();

        System.out.println("redission SetBucket OK");
    }


    @Test
    public void testGetJedis() {
        String key = "mytest_page";

        Page<Student> pages = redisProtocolBox.getObjFromRedis(key, new TypeReference<Page<Student>>() {});

        System.out.println(JSON.toJSONString(pages));

    }


    @Test
    public void testJackSon() {
        String key = "proxy_test_pageLLLL";


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

        Map<String, Integer> advisor = new HashMap<>();
        advisor.put("Stephon", 34);
        advisor.put("Tomiit", 56);
        pages.setAdvisor(advisor);

        redisProtocolBox.setObj2RedisWithExpSecond(key, pages, 300);

        Page<Student> myPage = redisProtocolBox.getObjFromRedis(key, new TypeReference<Page<Student>>() {});

        System.out.println(JSON.toJSONString(myPage));

    }

    @Test
    public void testMap2Jedis() {
        String key = "myMap_test";

        Map<String, Integer> advisor = new HashMap<>();
        advisor.put("Stephon", 34);
        advisor.put("Tomiit", 56);

        redisProtocolBox.setObj2RedisWithExpSecond(key, advisor, 500);

        Map<String, Integer> advisor1 = redisProtocolBox.getObjFromRedis(key, new TypeReference<Map<String, Integer>>() {});
        System.out.println(JSON.toJSONString(advisor1));
    }

}
