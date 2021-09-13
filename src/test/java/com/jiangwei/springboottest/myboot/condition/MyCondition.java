package com.jiangwei.springboottest.myboot.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author: weijiang
 * @date: 2021/9/2
 * @description:  spring条件接口的自定义实现
 **/
public class MyCondition implements Condition {

    /**
     * 条件匹配，true被@Conditional注解的类（配置类）会被配置解析注册到spring上下文容器中，
     *         false则相反
     * @param context
     * @param metadata
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return true;
    }
}
