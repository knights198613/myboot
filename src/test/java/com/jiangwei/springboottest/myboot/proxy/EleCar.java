package com.jiangwei.springboottest.myboot.proxy;

/**
 * @author: weijiang
 * @date: 2021/9/13
 * @description:
 **/
public class EleCar implements Car, EleCharging {

    @Override
    public void drive() {
        System.out.println("The EleCar drive quietly");
    }

    @Override
    public void eleRecharging() {
        System.out.println("The EleCar eleRecharging fast!");
    }
}
