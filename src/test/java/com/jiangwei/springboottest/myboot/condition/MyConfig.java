package com.jiangwei.springboottest.myboot.condition;

import com.jiangwei.springboottest.myboot.entity.ShopUserNoticeEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author: weijiang
 * @date: 2021/9/2
 * @description:
 **/
@Conditional({MyCondition.class})
@Configuration
public class MyConfig {

    @Bean
    public ShopUserNoticeEntity createEntity() {
        ShopUserNoticeEntity entity = new ShopUserNoticeEntity();
        entity.setUserId(7878L);
        entity.setStatus(1);
        return entity;
    }
}
