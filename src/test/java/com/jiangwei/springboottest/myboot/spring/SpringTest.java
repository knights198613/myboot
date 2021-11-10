package com.jiangwei.springboottest.myboot.spring;

import com.jiangwei.springboottest.myboot.MybootApplicationTests;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: weijiang
 * @date: 2021/11/8
 * @description:
 **/
public class SpringTest extends MybootApplicationTests {

    @Test
    public void startApp() {
        try {
            TimeUnit.SECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
