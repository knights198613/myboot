package com.jiangwei.springboottest.myboot.res;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author: weijiang
 * @date: 2021/9/1
 * @description:
 **/
public class MyResource implements AutoCloseable {

    @Override
    public void close() {
        System.out.println("close()");
    }

    public static MyResource getResource() {
        return new MyResource();
    }

    public void doAdd() {
        System.out.println("doAdd()");
    }
}
