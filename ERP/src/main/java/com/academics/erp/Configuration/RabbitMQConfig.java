package com.academics.erp.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "erp.salary.exchange";
    public static final String DISBURSE_QUEUE = "erp.salary.disburse.q";
    public static final String NOTIFY_QUEUE = "erp.salary.notify.q";
    public static final String DISBURSE_KEY = "salary.disburse";
    public static final String NOTIFY_KEY = "salary.notify";

    @Bean
    public TopicExchange exchange() { return new TopicExchange(EXCHANGE, true, false); }

    @Bean
    public Queue disburseQueue() { return QueueBuilder.durable(DISBURSE_QUEUE).build(); }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue notifyQueue() { return QueueBuilder.durable(NOTIFY_QUEUE).build(); }

    @Bean
    public Binding disburseBinding() { return BindingBuilder.bind(disburseQueue()).to(exchange()).with(DISBURSE_KEY); }

    @Bean
    public Binding notifyBinding() { return BindingBuilder.bind(notifyQueue()).to(exchange()).with(NOTIFY_KEY); }
}
