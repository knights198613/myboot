package com.jiangwei.springboottest.myboot.redis;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import com.jiangwei.springboottest.myboot.domains.Page;
import com.jiangwei.springboottest.myboot.domains.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class RedissonMapTest extends MybootApplicationTests {

    @Resource(name = "redisson")
    RedissonClient redissonClient;

    @Autowired
    Jedis jedis;

    private ObjectMapper objectMapper;

    static {

    }



    private void testJedis() {
        String key = "mytest_page";
        Page<Student> pages = getFromRedis(key, Page.class, Student.class);
        System.out.println(JSON.toJSONString(pages));
    }

    private <V> V getFromRedis(String key, Class... vClass) {
        String val = jedis.get(key);
        try {
            if(StringUtils.isNotEmpty(val)) {
                if(vClass != null && vClass.length == 1) {
                    JavaType javaType = objectMapper.getTypeFactory().constructType(vClass[0]);
                    return objectMapper.readValue(val, javaType);
                }else if(vClass != null && vClass.length > 1) {
                    Class<V> rawType = vClass[0];
                    Class<V>[] parametriedType = ArrayUtils.subarray(vClass, 1, vClass.length);
                    JavaType javaType = objectMapper.getTypeFactory().constructParametricType(rawType, parametriedType);
                    return objectMapper.readValue(val, javaType);
                }else {
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("_getFromRedis_ERROR", e);
        }
        return null;
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

        System.out.println("sssssssssss");

        testJedis();
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
