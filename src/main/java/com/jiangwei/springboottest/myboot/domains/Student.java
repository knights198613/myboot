package com.jiangwei.springboottest.myboot.domains;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: wuma (wuma@helijia.com)
 * @createDate: 2020/5/11
 * @company: (C) Copyright WWW.HELIJIA.COM 2020
 * @since: JDK 1.8
 * @description:
 */
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = -4276389062429394366L;

    private String name;
    private String from;
    private String sex;
    private int order;
}
