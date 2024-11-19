package com.academics.erp.controllers;

import com.academics.erp.DTO.RequestPasswordObj;
import com.academics.erp.entities.Employee;
import com.academics.erp.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PutMapping("/update-password")
    public String updatePassword(@RequestBody RequestPasswordObj passwordObj) {
        employeeService.updateEmployeePasswords(passwordObj);
        return "Password updated successfully";
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> allEmployees() {
        return ResponseEntity.ok(employeeService.allEmployees());
    }

}
