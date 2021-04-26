package com.jiangwei.springboottest.myboot.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: weijiang
 * @date: 2020/12/28
 * @description: 秒杀活动实体
 **/
@Data
public class FlashGoodCheapDTO implements Serializable {
    private static final long serialVersionUID = -2699823721153603975L;

    /**
     * 商品id
     */
    @JsonProperty("goodsId")
    private Long goodsId;
    /**
     * 价格 分
     */
    @JsonProperty("price")
    private Integer price;
    /**
     * 商品名
     */
    @JsonProperty("goodsName")
    private String goodsName;
    /**
     * 商品简称
     */
    @JsonProperty("shortName")
    private String shortName;
    /**
     * 划线价 元
     */
    @JsonProperty("linePrice")
    private BigDecimal linePrice;
    /**
     * 活动开始时间
     */
    @JsonProperty("startTime")
    private Long startTime;
}
