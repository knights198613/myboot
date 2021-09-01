package com.jiangwei.springboottest.myboot.functional;

/**
 * @author: weijiang
 * @date: 2021/8/27
 * @description: 测试函数式接口
 **/

@FunctionalInterface
public interface MyService {

    void doService(String msg);

    default String dotest() {
        return "doTest";
    }

    public int hashCode();
}



