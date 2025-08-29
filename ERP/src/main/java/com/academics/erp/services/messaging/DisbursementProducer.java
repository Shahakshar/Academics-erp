package com.academics.erp.services.messaging;

import com.academics.erp.configuration.RabbitMQConfig;
import com.academics.erp.dto.SalaryDisburseMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DisbursementProducer {
    private final RabbitTemplate rabbitTemplate;
    public DisbursementProducer(RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }
    public void send(SalaryDisburseMessage msg) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.DISBURSE_KEY, msg);
    }
}
