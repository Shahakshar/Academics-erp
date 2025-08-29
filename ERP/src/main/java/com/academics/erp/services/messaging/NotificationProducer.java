package com.academics.erp.services.messaging;

import com.academics.erp.configuration.RabbitMQConfig;
import com.academics.erp.dto.NotificationEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;
    public NotificationProducer(RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }
    public void send(NotificationEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.NOTIFY_KEY, event);
    }
}
