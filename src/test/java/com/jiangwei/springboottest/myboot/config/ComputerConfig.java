package com.jiangwei.springboottest.myboot.config;

import com.jiangwei.springboottest.myboot.domains.Computer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: weijiang
 * @date: 2021/1/7
 * @description:
 **/

@Configuration
public class ComputerConfig {

    @Bean
    public Computer getDell() {
        Computer computer = new Computer();
        computer.setName("DELL");
        computer.setPrice(6800.89D);
        return computer;
    }

    //@ConditionalOnMissingBean(Computer.class)
    @ConditionalOnBean(Computer.class)
    @Bean
    public Computer getMac() {
        Computer computer = new Computer();
        computer.setName("Mac Book Pro");
        computer.setPrice(10.45D);
        return computer;
    }


}
