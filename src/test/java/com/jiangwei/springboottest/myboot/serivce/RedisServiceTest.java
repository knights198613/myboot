package com.jiangwei.springboottest.myboot.serivce;

import com.alibaba.fastjson.JSON;
import com.helijia.framework.redis.AliyunRedisServiceImpl;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import com.jiangwei.springboottest.myboot.domains.Student;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author: wuma (wuma@helijia.com)
 * @createDate: 2020/5/11
 * @company: (C) Copyright WWW.HELIJIA.COM 2020
 * @since: JDK 1.8
 * @description:
 */
public class RedisServiceTest extends MybootApplicationTests {

    private final static String student_key = "sortedSetKey_students";

    @Resource(name = "redisService")
    AliyunRedisServiceImpl redisService;



    @Test
    public void testZADD() {
        Student student0 = new Student();
        student0.setFrom("beijing");
        student0.setName("xiaoming");
        student0.setSex("男");
        student0.setOrder(10);

        Student student1 = new Student();
        student1.setFrom("lanzhou");
        student1.setName("daxiong");
        student1.setSex("女");
        student1.setOrder(5);

        Student student2 = new Student();
        student2.setFrom("tianjin");
        student2.setName("shanjiang");
        student2.setSex("男");
        student2.setOrder(9);

        Student student3 = new Student();
        student3.setFrom("成都");
        student3.setName("qiuyu");
        student3.setSex("nv");
        student3.setOrder(9);

       redisService.zadd(student_key, student0.getOrder(), student0.toString());
       redisService.zadd(student_key, student1.getOrder(), student1.toString());
       redisService.zadd(student_key, student2.getOrder(), student2.toString());
       redisService.zadd(student_key, student3.getOrder(), student3.toString());

       System.out.println("###################");
    }

    @Test
    public void testZCOUNT() {
        double min = 0.00d;
        double max = 50.00d;
        Long result = redisService.zcount(student_key, min, max);
        System.out.println("返回SortedSet集合个数为："+ result);
    }

    @Test
    public void testZRANGE() {
        long start = 0L;
        long end = 20L;
        Set<String> result = redisService.zrange(student_key, start, end);

        System.out.println("返回结果："+ JSON.toJSONString(result));
    }

    @Test
    public void testZRANK() {
        Student student3 = new Student();
        student3.setFrom("成都");
        student3.setName("qiuyu");
        student3.setSex("nv");
        student3.setOrder(9);

        Student student0 = new Student();
        student0.setFrom("beijing");
        student0.setName("xiaoming");
        student0.setSex("男");
        student0.setOrder(10);

        Long result = redisService.zrank(student_key, student3.toString());
        System.out.println("返回student3排名为："+result);

        Long result1 = redisService.zrank(student_key, student0.toString());
        System.out.println("返回student0排名为："+result);
    }

    @Test
    public void testZREVRANGE() {
        Set<String> resultSet = redisService.zrevrange(student_key, 0L, 10L);
        System.out.println("返回sortedSet集合为："+ JSON.toJSONString(resultSet));

    }
}
