package com.academics.erp.controllers;

import com.academics.erp.entities.Notification;
import com.academics.erp.repository.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepo repo;

    @GetMapping("/{employeeId}")
    public List<Notification> getUnread(@PathVariable Long employeeId) {
        return repo.findByEmployeeId(employeeId);
    }

    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        repo.findById(id).ifPresent(n -> {
            n.setRead(true);
            repo.save(n);
        });
    }
}
