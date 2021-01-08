package com.jiangwei.springboottest.myboot.domains;

import com.alibaba.fastjson.JSON;
import com.jiangwei.springboottest.myboot.config.ComputerConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author: weijiang
 * @date: 2021/1/7
 * @description:
 **/
public class ComputerTest {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ComputerConfig.class);

    @Test
    public void testGetComputerBeans() {
        Map<String, Computer> computerMap = context.getBeansOfType(Computer.class);
        System.out.println(JSON.toJSONString(computerMap));
    }
 }