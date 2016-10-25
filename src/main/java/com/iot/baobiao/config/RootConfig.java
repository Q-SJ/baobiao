package com.iot.baobiao.config;

import com.iot.baobiao.rabbitmq.PaySuccessReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.p6spy.engine.spy.P6DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by ja on 2016/6/22.
 */

@Configuration
@Import({RabbitMQConfig.class, WebSocketConfig.class})
@EnableCaching
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = {"com.iot.baobiao.dao", "com.iot.baobiao.service"})
public class RootConfig {

    @Bean   //配置此Bean是为了使用属性占位符来注入值
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

//    @Bean
//    public P6DataSource dataSource() {
//        P6DataSource dataSource = new P6DataSource(dataSourceTarget());
//        return dataSource;
//    }

    @Bean
    public DataSource dataSource() {
        return dataSourceTarget();
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
