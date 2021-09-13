package com.jiangwei.springboottest.myboot.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author: weijiang
 * @date: 2021/9/13
 * @description:
 **/
public class TestCglib {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./class");
        Programmer programmer = new Programmer();
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(programmer.getClass());
        enhancer.setCallback(new Harker());
        Object proxy = enhancer.create();
        Programmer pg = (Programmer)proxy;
        pg.code();



    }
}
