package com.jiangwei.springboottest.myboot.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: wuma (wuma@helijia.com)
 * @createDate: 2020/5/11
 * @company: (C) Copyright WWW.HELIJIA.COM 2020
 * @since: JDK 1.8
 * @description: redis配置属性
 */
@ConfigurationProperties(prefix = "redis")
@Component
@Data
public class RedisConfig {

    private String address;

    private Integer idleConnectionTimeout;

    private Integer connectTimeout;

    private Integer timeout;

    private Integer retryAttempts;

    private Integer retryInterval;

    private Integer connectionMinimumIdleSize;

    private Integer connectionPoolSize;

    private String password;
}
