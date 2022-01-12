package com.jiangwei.springboottest.myboot.services;

import com.jiangwei.springboottest.myboot.domains.Student;
import org.springframework.stereotype.Service;

/**
 * @author: weijiang
 * @date: 2022/1/10
 * @description:
 **/
@Service
public class StudentService {

    public Student getStudentById(Integer id) {
        Student student = new Student();
        student.setId(id);
        student.setName("Jhon Smith");
        student.setSex("男");
        student.setOrder(1);
        student.setFrom("甘肃兰州");
        return student;
    }
}
