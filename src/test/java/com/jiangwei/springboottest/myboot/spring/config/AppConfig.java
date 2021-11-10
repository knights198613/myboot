package com.jiangwei.springboottest.myboot.spring.config;

import com.jiangwei.springboottest.myboot.spring.MyInstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.jiangwei.springboottest.myboot.spring.beans.*;

import javax.jws.soap.SOAPBinding;

/**
 * @author: weijiang
 * @date: 2021/11/8
 * @description:
 **/
@Configuration
public class AppConfig {

    @Bean
    public MyInstantiationAwareBeanPostProcessor createInstantiationPostProcessor() {
        MyInstantiationAwareBeanPostProcessor myInstantiationAwareBeanPostProcessor = new MyInstantiationAwareBeanPostProcessor();
        return myInstantiationAwareBeanPostProcessor;
    }


    @Bean
    public User createUser() {
        User user = new User();
        user.setId(123456L);
        user.setAddress("北京市西城区皮条胡同1127号");
        user.setName("驴谦");
        user.setSex(1);
        user.setTelPhone("13234992779");
        return user;
    }
}
