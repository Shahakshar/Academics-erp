package com.academics.erp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name= "employee_salary")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private Date payment_date;

    @Column(nullable = false)
    private double amount;

    @Column
    private String description;

    @Column(nullable = false)
    private int employee_id;

}
