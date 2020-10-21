package com.umer.alihealth.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.umer.*.mapper")
public class DBConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }
}
