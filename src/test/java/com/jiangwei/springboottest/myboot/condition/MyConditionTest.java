package com.jiangwei.springboottest.myboot.condition;

import com.jiangwei.springboottest.myboot.entity.ShopUserNoticeEntity;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author: weijiang
 * @date: 2021/9/2
 * @description: 测试注解@Condition及自定义Condition
 **/
public class MyConditionTest {


    @Test
    public void testCondition() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        //获取@bean的注入bean
        Map<String, ShopUserNoticeEntity> beansMap = annotationConfigApplicationContext.getBeansOfType(ShopUserNoticeEntity.class);
        beansMap.forEach((k,v)-> System.out.println("beanName="+k+", beanValue="+v.toString()));

        System.out.println("以下打印输出为配置类信息：");
        //获取@Configuration配置解析到IOC容器中的配置类
        Map<String, MyConfig> configsMap = annotationConfigApplicationContext.getBeansOfType(MyConfig.class);
        configsMap.forEach((k, v)-> System.out.println("beanName="+k+", beanValue="+v.toString()));

    }
}
