package com.jiangwei.springboottest.myboot.stream;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author: weijiang
 * @date: 2021/3/11
 * @description:
 **/
public class StreamTest {


    @Test
    public void testToArray() {

        List<String> strList = Lists.newArrayList();
        strList.add("1");
        strList.add("2");
        strList.add("3");
        strList.add("4");
        strList.add("5");
        strList.add("8");

        String[] arr = strList.stream().toArray(String[]::new);

        System.out.println(arr);
    }
}
