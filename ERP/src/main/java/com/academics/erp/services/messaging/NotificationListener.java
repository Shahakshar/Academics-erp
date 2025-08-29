package com.academics.erp.services.messaging;

import com.academics.erp.configuration.RabbitMQConfig;
import com.academics.erp.dto.NotificationEvent;
import com.academics.erp.entities.Notification;
import com.academics.erp.repository.NotificationRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class NotificationListener {
    private final NotificationRepo repo;
    public NotificationListener(NotificationRepo repo) { this.repo = repo; }

    @RabbitListener(queues = RabbitMQConfig.NOTIFY_QUEUE)
    public void handle(NotificationEvent e) {
        repo.save(Notification.builder()
                .employeeId(e.getEmployeeId())
                .message(e.getMessage())
                .createdAt(LocalDateTime.now())
                .build());
    }
}
