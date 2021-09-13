package com.jiangwei.springboottest.myboot.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author: weijiang
 * @date: 2021/9/13
 * @description: 汽车的测试类
 **/
public class CarTest {

    public static void main(String[] args) {
        EleCar eleCar = new EleCar();
        Class eleCarClass = eleCar.getClass();
        Object carProxy = Proxy.newProxyInstance(eleCarClass.getClassLoader(), eleCarClass.getInterfaces(), new CarsInvocationHandler(eleCar));

        Car car = (Car)carProxy;
        car.drive();

        EleCharging eleCharging = (EleCharging)carProxy;

        eleCharging.eleRecharging();

        ProxyUtil.writeProxy2Disk("EleCarProxy", eleCar.getClass());
    }


}
