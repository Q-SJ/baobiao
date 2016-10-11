package com.iot.baobiao.config;

import com.p6spy.engine.spy.P6DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by ja on 2016/6/22.
 */

@Configuration
@EnableCaching
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = {"com.iot.baobiao.dao", "com.iot.baobiao.service"})
public class RootConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    //配置MySQL数据源，此为DBCP数据源连接池
    @Bean
    public BasicDataSource dataSourceTarget() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://120.25.162.238:3306/nutch?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("linaro");
//        dataSource.setDriverClassName("${jdbc.driver}");
//        dataSource.setUrl("${jdbc.url}");
//        dataSource.setUsername("${jdbc.name}");
//        dataSource.setPassword("${jdbc.password}");
        dataSource.setMaxActive(30);
        dataSource.setMaxIdle(5);
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }

    @Bean
    public P6DataSource dataSource() {
        P6DataSource dataSource = new P6DataSource(dataSourceTarget());
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    //声明缓存管理器
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    //配置事务管理器
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        return transactionManager;
    }
}
