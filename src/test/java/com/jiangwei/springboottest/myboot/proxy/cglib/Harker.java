package com.jiangwei.springboottest.myboot.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: weijiang
 * @date: 2021/9/13
 * @description:
 **/
public class Harker implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("call before !!!");
        Object obj = methodProxy.invokeSuper(o, objects);
        System.out.println("call after !!!");
        return obj;
    }
}
