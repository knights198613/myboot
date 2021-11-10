package com.jiangwei.springboottest.myboot.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: weijiang
 * @date: 2021/11/8
 * @description:
 **/
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("Method postProcessBeforeInstantiation........"+"beanClass = " + beanClass + ", beanName = " + beanName);
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("Method postProcessAfterInstantiation###########"+"bean = " + bean + ", beanName = " + beanName);
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("Method postProcessProperties@@@@@@@@@@@@@@"+"pvs = " + pvs + ", bean = " + bean + ", beanName = " + beanName);
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Method postProcessBeforeInitialization^^^^^^^^^^^^^^^^^^^^^"+"bean = " + bean + ", beanName = " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Method postProcessAfterInitialization*************"+"bean = " + bean + ", beanName = " + beanName);
        return bean;
    }
}
