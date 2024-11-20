package com.academics.erp.controllers;

import com.academics.erp.DTO.AccountResponse;
import com.academics.erp.entities.Employee;
import com.academics.erp.entities.EmployeeSalary;
import com.academics.erp.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/account")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/amount")
    public ResponseEntity<List<AccountResponse>> getEmployeeDetailsByCredential() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = (Employee) auth.getPrincipal();
        if (employee.getDepartment().getDepartment_id().equals(2)) {
            return ResponseEntity.ok(accountService.getAllEmployeeDetails());
        } else {
            Optional<EmployeeSalary> detail = accountService.getAccountDetails(employee.getEmployee_id());
            return ResponseEntity.ok(List.of(new AccountResponse().setEmployee(employee).setEmployeeSalary(detail.get())));
        }
    }

}
