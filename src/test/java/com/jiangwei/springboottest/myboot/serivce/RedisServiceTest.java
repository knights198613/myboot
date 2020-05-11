package com.jiangwei.springboottest.myboot.serivce;

import com.helijia.framework.redis.AliyunRedisServiceImpl;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import com.jiangwei.springboottest.myboot.domains.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

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

       redisService.zadd(student_key, student0.getOrder(), student0.toString());
       redisService.zadd(student_key, student1.getOrder(), student1.toString());

       System.out.println("###################");
    }
}
