package com.jiangwei.springboottest.myboot.domains;

import lombok.Data;

import java.util.List;

/**
 * @author: weijiang
 * @date: 2021/5/8
 * @description:
 **/

@Data
public class Page<T> {

    private boolean status;

    private List<T> data;

    private Integer pageSize;

    private Integer pageNum;

    private Integer totalNum;


}
