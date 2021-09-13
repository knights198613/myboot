package com.jiangwei.springboottest.myboot.proxy;

import sun.misc.ProxyGenerator;

import java.io.*;

/**
 * @author: weijiang
 * @date: 2021/9/13
 * @description:
 **/
public class ProxyUtil {

    public  static void writeProxy2Disk(String proxyName, Class clazz) {
        byte[] result = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        File file = new File("./"+proxyName+".class");
        FileOutputStream fileOutputStream  = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null)
                try {
                    fileOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
