package com.academics.erp.services;

import com.academics.erp.dto.AccountResponse;
import com.academics.erp.dto.SalaryDisburseMessage;
import com.academics.erp.entities.Employee;
import com.academics.erp.entities.EmployeeSalary;
import com.academics.erp.repository.AccountRepo;
import com.academics.erp.services.messaging.DisbursementProducer;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepo accountRepo;
    private final DisbursementProducer disbursementProducer;

    public AccountService(AccountRepo accountRepo, DisbursementProducer disbursementProducer) {
        this.accountRepo = accountRepo;
        this.disbursementProducer = disbursementProducer;
    }

    public Optional<EmployeeSalary> getAccountDetails(Integer id) {
        return accountRepo.findById(id);
    }

    public List<AccountResponse> getAllEmployeeDetails() {
        return accountRepo.findAllEmployeeDetails();
    }

    public String updateAmount(List<EmployeeSalary> employeeSalary, Employee currEmployee) {

        employeeSalary.stream()
                .filter(s -> s.getEmployee_id() != currEmployee.getEmployee_id())
                .forEach(s -> disbursementProducer.send(
                        SalaryDisburseMessage.builder()
                                .employeeId(s.getEmployee_id())
                                .amount(s.getAmount())
                                .description(s.getDescription())
                                .paymentDate(s.getPayment_date())
                                .build()
                ));
        return "queued";

    }

    public void saveSalary(@Valid EmployeeSalary employeeSalary) {

        disbursementProducer.send(
                SalaryDisburseMessage.builder()
                        .employeeId(employeeSalary.getEmployee_id())
                        .amount(employeeSalary.getAmount())
                        .description(employeeSalary.getDescription())
                        .paymentDate(employeeSalary.getPayment_date())
                        .build()
        );
    }
}


//public void saveSalary(@Valid EmployeeSalary employeeSalary) {
//
//        if (currentEmployee.getDepartment().getDepartment_id() != 2) {
//            throw new IllegalArgumentException("You are not authorized to perform this operation.");
//        }

//        accountRepo.updateSalaryOfEmployee(
//                employeeSalary.getAmount(),
//                employeeSalary.getDescription(),
//                employeeSalary.getPayment_date(),
//                employeeSalary.getEmployee_id()
//        );
//}

//public String updateAmount(List<EmployeeSalary> employeeSalary, Employee currEmployee) {

//        List<EmployeeSalary> empSalaryList = accountRepo.getAllEmployeeSalary();
//
//        Map<Integer, EmployeeSalary> newSalaryMap = employeeSalary.stream()
//                .filter(salary -> salary.getEmployee_id() != currEmployee.getEmployee_id())
//                .collect(Collectors.toMap(EmployeeSalary::getEmployee_id, salary -> salary));
//
//        // Update the amounts in the existing salary list
//        empSalaryList.forEach(existingSalary -> {
//            if (newSalaryMap.containsKey(existingSalary.getEmployee_id())) {
//                EmployeeSalary newSalary = newSalaryMap.get(existingSalary.getEmployee_id());
//                existingSalary.setAmount(existingSalary.getAmount() + newSalary.getAmount());
//                existingSalary.setDescription(newSalary.getDescription());
//                existingSalary.setPayment_date(newSalary.getPayment_date());
//            }
//        });
// }