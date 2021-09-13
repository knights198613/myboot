package com.jiangwei.springboottest.myboot.condition1;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @author: weijiang
 * @date: 2021/9/2
 * @description: 当没有发现注入想要的bean时才注入
 **/
public class OnMissBeanCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, IService> serviceMap = context.getBeanFactory().getBeansOfType(IService.class);
        if(serviceMap.isEmpty())
            return true;
        return false;
    }
}
