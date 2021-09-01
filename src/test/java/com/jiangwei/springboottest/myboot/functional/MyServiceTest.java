package com.jiangwei.springboottest.myboot.functional;

/**
 * @author: weijiang
 * @date: 2021/8/27
 * @description:
 **/
public class MyServiceTest {

    public static void main(String[] args) {
        MyService myService = msg -> System.out.println("I am beginning service for "+ msg);
        /*MyService myService = new MyService() {
            @Override
            public void doService(String msg) {
                System.out.println("I am beginning service for "+ msg);
            }
        };*/
        myService.doService("eat meat");
    }
}
