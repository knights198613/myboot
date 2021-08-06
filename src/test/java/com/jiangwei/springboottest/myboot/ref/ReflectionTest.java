package com.jiangwei.springboottest.myboot.ref;

import org.junit.Test;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.util.Arrays;

/**
 * @author: weijiang
 * @date: 2021/8/3
 * @description:
 **/

public class ReflectionTest {

    public static void main(String[] args) {
        myTest();
    }


    @CallerSensitive
    public static void myTest() {
        Class<?> caller = Reflection.getCallerClass();
        System.out.println(caller.getClassLoader());
    }
}
