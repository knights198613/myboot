package com.jiangwei.springboottest.myboot.beans;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author: weijiang
 * @date: 2022/3/4
 * @description:
 **/
@Component("studentBean")
public class StudentBean implements FactoryBean {

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Object getObject() throws Exception {
        return new TeacherBean();
    }

    @Override
    public Class<?> getObjectType() {
        return StudentBean.class;
    }

    public void study() {
        System.out.println("学生学习￥￥￥￥￥");
    }
}
