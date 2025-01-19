package com.yevmie.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String QUEUE_NOTIFICATION = "notification.queue";
    public static final String QUEUE_AUDIT = "audit.queue";
    public static final String QUEUE_REPORT = "report.queue";
    
    public static final String EXCHANGE_NOTIFICATION = "notification.exchange";
    public static final String EXCHANGE_DIRECT = "yevmie.direct";
    
    public static final String ROUTING_KEY_NOTIFICATION = "notification.routing";
    public static final String ROUTING_KEY_AUDIT = "audit";
    public static final String ROUTING_KEY_REPORT = "report";

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(jacksonConverter());
        return template;
    }

    @Bean
    public MessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NOTIFICATION, true);
    }

    @Bean
    public Queue auditQueue() {
        return new Queue(QUEUE_AUDIT, true);
    }

    @Bean
    public Queue reportQueue() {
        return new Queue(QUEUE_REPORT, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NOTIFICATION);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, DirectExchange exchange) {
        return BindingBuilder
            .bind(notificationQueue)
            .to(exchange)
            .with(ROUTING_KEY_NOTIFICATION);
    }

    @Bean
    public Binding auditBinding(Queue auditQueue, DirectExchange exchange) {
        return BindingBuilder
            .bind(auditQueue)
            .to(exchange)
            .with(ROUTING_KEY_AUDIT);
    }

    @Bean
    public Binding reportBinding(Queue reportQueue, DirectExchange exchange) {
        return BindingBuilder
            .bind(reportQueue)
            .to(exchange)
            .with(ROUTING_KEY_REPORT);
    }
} 