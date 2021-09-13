package com.jiangwei.springboottest.myboot.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: weijiang
 * @date: 2021/9/13
 * @description: 汽车的代理处理器
 **/
public class CarsInvocationHandler implements InvocationHandler {

    EleCar eleCar;

    public CarsInvocationHandler(EleCar eleCar) {
        this.eleCar = eleCar;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        System.out.println("called Before the Method:"+ methodName);
        Object obj = method.invoke(eleCar, args);
        System.out.println("called After the Method:"+ methodName);
        return obj;
    }
}
