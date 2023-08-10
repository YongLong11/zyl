package com.ziroom.zyl.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DataSourceConfig {
    /**
     *
     * @return
     */
    @Bean(name = DataSourceConstant.ZYL)
    @ConfigurationProperties(prefix = "spring.datasource.hikari.zyl")
    public DataSource zylDataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
        return DataSourceBuilder.create().build();
    }

    /**
     *
     * @return
     */
    @Bean(name = DataSourceConstant.OKR)
    @ConfigurationProperties(prefix = "spring.datasource.hikari.okr")
    public DataSource okrDataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
        return DataSourceBuilder.create().build();
    }


    /**
     * 获取动态数据源
     * @return
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(zylDataSource());
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceConstant.OKR,okrDataSource());
        dataSourceMap.put(DataSourceConstant.ZYL,zylDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

}
