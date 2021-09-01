package com.jiangwei.springboottest.myboot.res;

import java.io.IOException;

/**
 * @author: weijiang
 * @date: 2021/9/1
 * @description:
 **/
public class UseResource {



    public static void main(String[] args) {
         UseResource useResource = new UseResource();
         useResource.addUser();
    }


    public void addUser() {
        try(MyResource myResource = MyResource.getResource()) {
            doSomething();
            myResource.doAdd();
        }
    }


    public void doSomething() {
        System.out.println("doSomething()");
    }
}
