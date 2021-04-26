package com.jiangwei.springboottest.myboot.serivce;

import com.alibaba.fastjson.JSON;
import com.jiangwei.springboottest.myboot.domains.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: weijiang
 * @date: 2021/3/26
 * @description:
 **/
public class StudentTest {


    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        Student st1 = new Student();
        st1.setId(1);
        st1.setName("1111");

        Student st2 = new Student();
        st2.setName("2222");
        st2.setId(2);

        studentList.add(st1);
        studentList.add(st2);

        String st = JSON.toJSONString(studentList);

        List<Student> list = new ArrayList<>();

        list = JSON.parseArray(st, Student.class);
        System.out.println("args = " + Arrays.deepToString(args));
    }
}
