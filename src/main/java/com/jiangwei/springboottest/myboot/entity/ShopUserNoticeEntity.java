package com.jiangwei.springboottest.myboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: weijiang
 * @date: 2021/2/23
 * @description:
 **/

@Data
public class ShopUserNoticeEntity implements Serializable {
    private static final long serialVersionUID = 9200830905659163006L;

    private Long id;

    private Integer status;

    private Date createTime;

    private String endTime;

    private Long userId;

    private Long goodsId;

    private Integer messageType;

    private String jumpUrl;

    private String param;

    private Integer activityType;

    private Date replenishmentTime;
}
