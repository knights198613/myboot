package com.jiangwei.springboottest.myboot.domains;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author: weijiang
 * @date: 2021/1/7
 * @description:
 **/
public class StudentTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(StudentConfig.class);

    @Test
    public void studentConfigTest() {
        System.out.println(applicationContext.getEnvironment().getProperty("per.name"));
        Map<String, Student> beanMap = applicationContext.getBeansOfType(Student.class);
        System.out.println(JSON.toJSONString(beanMap));
    }
}
