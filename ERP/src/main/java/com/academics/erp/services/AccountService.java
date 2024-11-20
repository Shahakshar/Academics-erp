package com.academics.erp.services;

import com.academics.erp.DTO.AccountResponse;
import com.academics.erp.entities.Employee;
import com.academics.erp.entities.EmployeeSalary;
import com.academics.erp.repository.AccountRepo;
import com.academics.erp.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepo accountRepo;
    private final EmployeeRepo employeeRepo;

    public AccountService(AccountRepo accountRepo, EmployeeRepo employeeRepo) {
        this.accountRepo = accountRepo;
        this.employeeRepo = employeeRepo;
    }

    public Optional<EmployeeSalary> getAccountDetails(Integer id) {
        return accountRepo.findById(id);
    }

    public List<AccountResponse> getAllEmployeeDetails() {
        List<Employee> employees = employeeRepo.findAll();
        List<AccountResponse> accountResponses = new ArrayList<>();

        for (Employee employee : employees) {
            Optional<EmployeeSalary> employeeSalary = accountRepo.findById(employee.getEmployee_id());
            AccountResponse accountResponse = new AccountResponse()
                    .setEmployee(employee)
                    .setEmployeeSalary(employeeSalary.orElse(null));
            accountResponses.add(accountResponse);
        }

        return accountResponses;
    }
}
