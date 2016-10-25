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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jia on 2016/10/17.
 */
@Configuration
public class RabbitMQConfig {
    //    以下配置RabbitMQ消息服务
    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory());
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }

    @Bean
    TopicExchange paySuccessExchange() {
        return new TopicExchange("pay-success-exchange");
    }

    @Bean
    Queue queue() {
        return new Queue("pay-success-queue", false);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("pay-success");
    }

    @Bean
    RabbitAdmin admin() {
        RabbitAdmin admin = new RabbitAdmin(rabbitConnectionFactory());
        admin.setIgnoreDeclarationExceptions(true); //这样即使有关rabbitmq的bean初始化失败整个web应用还能正常启动
        return admin;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory rabbitConnectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory);
        container.setQueueNames("pay-success-queue");
        container.setMessageListener(listenerAdapter);
        container.setMessageConverter(jsonMessageConverter());
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(PaySuccessReceiver receiver) {
        MessageListenerAdapter m = new MessageListenerAdapter(receiver, "handleMessage");
        m.setMessageConverter(jsonMessageConverter());
        return m;
    }

    @Bean
    public PaySuccessReceiver receiver() {
        return new PaySuccessReceiver();
    }
}
