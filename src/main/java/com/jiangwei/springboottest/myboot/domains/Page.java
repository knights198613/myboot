package com.jiangwei.springboottest.myboot.domains;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: weijiang
 * @date: 2021/5/8
 * @description:
 **/

@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -7396594884367706052L;


    private boolean status;

    private List<T> data;

    private Integer pageSize;

    private Integer pageNum;

    private Integer totalNum;


}
