package com.academics.erp.services;

import com.academics.erp.DTO.AccountResponse;
import com.academics.erp.entities.Employee;
import com.academics.erp.entities.EmployeeSalary;
import com.academics.erp.repository.AccountRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Optional<EmployeeSalary> getAccountDetails(Integer id) {
        return accountRepo.findById(id);
    }

    public List<AccountResponse> getAllEmployeeDetails() {
        return accountRepo.findAllEmployeeDetails();
    }

    public String updateAmount(List<EmployeeSalary> employeeSalary, Employee currEmployee) {

        List<EmployeeSalary> empSalaryList = accountRepo.getAllEmployeeSalary();

        Map<Integer, EmployeeSalary> newSalaryMap = employeeSalary.stream()
                .filter(salary -> salary.getEmployee_id() != currEmployee.getEmployee_id())
                .collect(Collectors.toMap(EmployeeSalary::getEmployee_id, salary -> salary));

        // Update the amounts in the existing salary list
        empSalaryList.forEach(existingSalary -> {
            if (newSalaryMap.containsKey(existingSalary.getEmployee_id())) {
                EmployeeSalary newSalary = newSalaryMap.get(existingSalary.getEmployee_id());
                existingSalary.setAmount(existingSalary.getAmount() + newSalary.getAmount());
            }
        });

        accountRepo.saveAll(empSalaryList);
        return "success!";

    }
}
