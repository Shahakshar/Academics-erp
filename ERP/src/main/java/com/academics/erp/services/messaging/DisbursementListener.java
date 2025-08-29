package com.academics.erp.services.messaging;

import com.academics.erp.configuration.RabbitMQConfig;
import com.academics.erp.dto.NotificationEvent;
import com.academics.erp.dto.SalaryDisburseMessage;
import com.academics.erp.entities.EmployeeSalary;
import com.academics.erp.repository.AccountRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DisbursementListener {
    private final AccountRepo accountRepo;
    private final NotificationProducer notificationProducer;

    public DisbursementListener(AccountRepo accountRepo, NotificationProducer notificationProducer) {
        this.accountRepo = accountRepo;
        this.notificationProducer = notificationProducer;
    }

    @RabbitListener(queues = RabbitMQConfig.DISBURSE_QUEUE)
    public void handle(SalaryDisburseMessage m) {
        // if salary reacord exists
        Optional<EmployeeSalary> existing = accountRepo.findById(m.getEmployeeId());

        EmployeeSalary es = existing.orElseGet(() -> {
            EmployeeSalary newEs = new EmployeeSalary();
            newEs.setEmployee_id(m.getEmployeeId());
            return newEs;
        });

        es.setAmount(m.getAmount());
        es.setDescription(m.getDescription());
        es.setPayment_date(m.getPaymentDate());
        accountRepo.save(es);

        notificationProducer.send(
                NotificationEvent.builder()
                        .employeeId(m.getEmployeeId())
                        .message("Salary ₹" + m.getAmount() + " disbursed")
                        .build()
        );
    }
}
