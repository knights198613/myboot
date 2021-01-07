package com.jiangwei.springboottest.myboot.domains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author: weijiang
 * @date: 2021/1/7
 * @description:
 **/
@Configuration
public class StudentConfig {

    @Conditional({BillGatesCondition.class})
    @Bean(name = "billGates")
    public Student getStd1() {
        Student st = new Student();
        st.setName("billGates");
        st.setId(1);
        return st;
    }


    @Conditional({LinusCondition.class})
    @Bean(name = "linus")
    public Student getStd2() {
        Student st = new Student();
        st.setName("Linus");
        st.setId(2);
        return st;
    }

}
