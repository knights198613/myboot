package com.jiangwei.springboottest.myboot.config;

import com.jiangwei.springboottest.myboot.domains.Animals;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: weijiang
 * @date: 2021/1/8
 * @description:
 **/

@Configuration
@PropertySource("classpath:/conf.properties")
public class AnimalsConfig {

    @Bean
    @ConditionalOnProperty(name = {"cat.enable"}, prefix = "animal", matchIfMissing=false, havingValue="true")
    public Animals getAnimal() {
        Animals cat = new Animals();
        cat.setName("Kitty");
        cat.setAge(4);
        return cat;
    }
}
