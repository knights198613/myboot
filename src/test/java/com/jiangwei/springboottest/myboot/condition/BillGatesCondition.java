package com.jiangwei.springboottest.myboot.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author: weijiang
 * @date: 2021/1/7
 * @description: mac系统名条件类
 **/
public class BillGatesCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String perName = context.getEnvironment().getProperty("per.name");
        if("bill".equals(perName)) {
            return true;
        }
        return false;
    }
}
