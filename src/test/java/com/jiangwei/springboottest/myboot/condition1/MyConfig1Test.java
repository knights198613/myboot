package com.jiangwei.springboottest.myboot.condition1;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author: weijiang
 * @date: 2021/9/2
 * @description: 测试类
 **/
public class MyConfig1Test {

    @Test
    public void testMyConfig1() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyConfig1.class);
        Map<String, IService> serviceMap = annotationConfigApplicationContext.getBeansOfType(IService.class);

        serviceMap.forEach((k, v) -> System.out.println("beanName="+k+", beanValue="+v.toString()));
    }
}
