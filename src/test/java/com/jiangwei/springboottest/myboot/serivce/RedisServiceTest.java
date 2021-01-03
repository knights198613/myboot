package com.jiangwei.springboottest.myboot.serivce;

import com.alibaba.fastjson.JSON;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import com.jiangwei.springboottest.myboot.domains.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.client.codec.StringCodec;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wuma (wuma@helijia.com)
 * @createDate: 2020/5/11
 * @company: (C) Copyright WWW.HELIJIA.COM 2020
 * @since: JDK 1.8
 * @description:
 */
@Slf4j
public class RedisServiceTest extends MybootApplicationTests {

    private final static String student_key = "sortedSetKey_students";

    @Resource(name = "redisson")
    Redisson redisson;


    @Test
    public void testSaveObj() {
        Student std = new Student();
        std.setFrom("HeBei");
        std.setName("John Tommy");
        std.setOrder(1);
        std.setSex("nan");
        std.setId(123);

        List<Student> myList = new ArrayList<>();
        myList.add(std);
        Map<String, List<Student>> map = new HashMap<>();
        map.put("3243UIJjiji", myList);

        save2Redis(map);

        map = getStudentFromRedis();

        System.out.println(JSON.toJSONString(map));
    }


    private void save2Redis(Map<String, List<Student>> map) {
        String key = "std_123";
        RBucket rBucket = redisson.getBucket(key);
        rBucket.set(map);
    }

    private <V> V getStudentFromRedis() {
        try {
            String key = "std_123";
            RBucket<V> rBucket = redisson.getBucket(key);
            return rBucket.get();
        } catch (Exception e) {
            log.error("get has error", e);
            throw e;
        }
    }



}
