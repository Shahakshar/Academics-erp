package com.academics.erp.dto;

import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SalaryDisburseMessage implements Serializable {
    private int employeeId;
    private double amount;
    private String description;
    private Date paymentDate;
}
