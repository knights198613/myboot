package com.jiangwei.springboottest.myboot.spring.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: weijiang
 * @date: 2021/11/8
 * @description:
 **/

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -4446992869788068563L;

    private Long id;

    private String name;

    private String address;
    /**
     * 1: man
     * 2: women
     */
    private Integer sex;

    private String telPhone;


    public void initMethod() {
        System.out.println("class User method initMethod &&&&&&&&&&&&&&");
    }


}
