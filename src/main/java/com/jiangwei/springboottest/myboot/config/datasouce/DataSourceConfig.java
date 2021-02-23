package com.jiangwei.springboottest.myboot.config.datasouce;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author: weijiang
 * @date: 2021/2/23
 * @description:
 **/
@ConditionalOnClass(HikariDataSource.class)
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(name="datasource.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikaricp")
    public HikariDataSource createDatasource(DataSourceProperties dataSourceProperties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dataSourceProperties.getUrl());
        config.setPassword(dataSourceProperties.getPassword());
        config.setUsername(dataSourceProperties.getUsername());
        config.setDriverClassName(dataSourceProperties.getDriverClassName());
        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource;

    }


}
