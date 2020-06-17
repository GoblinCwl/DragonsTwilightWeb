package com.goblincwl.dragontwilight.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;


@Configuration
@PropertySource(value = {"/properties/dataSource.properties"})
public class DataSourceConfig {

    /**
     * 主DataSource 配置
     */
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean(name = "primaryDruidDataSource")
    public DataSource primaryDruidDataSource(Environment environment) {
        return DruidDataSourceBuilder.create().build(environment, "spring.datasource.druid.");
    }


    /**
     * 从DataSource 配置
     */
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    @Bean(name = "slaveDruidDataSource")
    public DataSource slaveDruidDataSource(Environment environment) {
        return DruidDataSourceBuilder.create().build(environment, "spring.datasource.druid.slave.");
    }


}
