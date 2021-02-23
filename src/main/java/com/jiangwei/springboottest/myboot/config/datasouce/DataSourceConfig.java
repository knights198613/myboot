package com.jiangwei.springboottest.myboot.config.datasouce;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: weijiang
 * @date: 2021/2/23
 * @description:
 **/
@Configuration
public class DataSourceConfig {

    @Bean("hikariConfig")
    @ConfigurationProperties(prefix = "hikaricp")
    public HikariConfig createConfig() {
        HikariConfig config = new HikariConfig();
        return config;
    }

    @Bean
    public HikariDataSource createDatasource(DataSourceProperties dataSourceProperties, HikariConfig hikariConfig) {
        hikariConfig.setJdbcUrl(dataSourceProperties.getUrl());
        hikariConfig.setPassword(dataSourceProperties.getPassword());
        hikariConfig.setUsername(dataSourceProperties.getUsername());
        hikariConfig.setDriverClassName(dataSourceProperties.getDriverClassName());
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }




}
