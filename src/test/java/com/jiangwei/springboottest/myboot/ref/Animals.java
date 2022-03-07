package com.jiangwei.springboottest.myboot.ref;

/**
 * @author: weijiang
 * @date: 2022/2/28
 * @description:
 **/
public class Animals {

    public String name;
    private Integer age;


    public Animals() {
    }

    private Animals(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    private Integer getAge() {
        return age;
    }


}
