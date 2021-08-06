package com.jiangwei.springboottest.myboot.security;

import java.util.Arrays;

/**
 * @author: weijiang
 * @date: 2021/8/5
 * @description: java 安全管理器测试
 **/
public class MySecurityTest {

    static class MySecurity extends SecurityManager {
        public void checkDo(int status) throws SecurityException {
            if(status == 0)
                throw new SecurityException("status==0");
            return;
        }
    }

    public static void main(String[] args) {
        MySecurity mySecurity = new MySecurity();
        SecurityManager sm = System.getSecurityManager();
        System.out.println(sm);
        System.setSecurityManager(mySecurity);
        mySecurity.checkDo(1);
        System.exit(0);
    }
}
