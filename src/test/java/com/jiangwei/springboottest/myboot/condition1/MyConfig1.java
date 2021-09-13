package com.jiangwei.springboottest.myboot.condition1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author: weijiang
 * @date: 2021/9/2
 * @description:
 **/
@Configuration
public class MyConfig1 {
    @Conditional({OnMissBeanCondition.class})
    @Bean
    public IService createService1() {
        return new Service1();
    }

    @Conditional({OnMissBeanCondition.class})
    @Bean
    public IService createService2() {
        return new Service2();
    }
}
