package com.jiangwei.springboottest.myboot.beans;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: weijiang
 * @date: 2022/3/4
 * @description:
 **/
public class StudentBeanTest {

    @Test
    public void testFactoryBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        TeacherBean teacherBean = (TeacherBean) applicationContext.getBean("studentBean");
        teacherBean.teacher();

        StudentBean studentBean = (StudentBean) applicationContext.getBean("&studentBean");
        studentBean.study();
    }
}
