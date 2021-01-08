package com.jiangwei.springboottest.myboot.domains;

import com.alibaba.fastjson.JSON;
import com.jiangwei.springboottest.myboot.config.AnimalsConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: weijiang
 * @date: 2021/1/8
 * @description:
 **/
public class AnimalsTest {

    AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(AnimalsConfig.class);

    @Test
    public void testAnimal() {
        Animals cat = configApplicationContext.getBean(Animals.class);

        System.out.println(JSON.toJSONString(cat));
    }
}
