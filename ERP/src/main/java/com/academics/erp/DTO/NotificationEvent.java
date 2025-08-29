package com.academics.erp.dto;

import lombok.*;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationEvent implements Serializable {
    private int employeeId;
    private String message;
}
