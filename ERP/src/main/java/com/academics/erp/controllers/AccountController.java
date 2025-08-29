package com.academics.erp.controllers;

import com.academics.erp.dto.AccountResponse;
import com.academics.erp.dto.ApiResponse;
import com.academics.erp.entities.Employee;
import com.academics.erp.entities.EmployeeSalary;
import com.academics.erp.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getEmployeeDetailsByCredential() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = (Employee) auth.getPrincipal();

        try {
            // Check if the employee belongs to department ID 2
            if (employee.getDepartment().getDepartment_id().equals(2)) {
                List<AccountResponse> allEmployeeDetails = accountService.getAllEmployeeDetails();

                ApiResponse<List<AccountResponse>> response = ApiResponse.<List<AccountResponse>>builder()
                        .success(true)
                        .message("All employee details fetched successfully")
                        .data(allEmployeeDetails)
                        .statusCode(HttpStatus.OK.value())
                        .build();

                return ResponseEntity.ok(response);
            } else {
                // Fetch the employee's own details
                Optional<EmployeeSalary> salary = accountService.getAccountDetails(employee.getEmployee_id());

                if (salary.isPresent()) {
                    AccountResponse details = AccountResponse.builder()
                            .employee_id(employee.getEmployee_id())
                            .first_name(employee.getFirst_name())
                            .last_name(employee.getLast_name())
                            .email(employee.getEmail())
                            .title(employee.getTitle())
                            .photograph_path(employee.getPhotograph_path())
                            .department(employee.getDepartment())
                            .employeeSalary(salary.get())
                            .build();

                    ApiResponse<List<AccountResponse>> response = ApiResponse.<List<AccountResponse>>builder()
                            .success(true)
                            .message("Employee details fetched successfully")
                            .data(List.of(details))
                            .statusCode(HttpStatus.OK.value())
                            .build();

                    return ResponseEntity.ok(response);
                } else {
                    ApiResponse<List<AccountResponse>> errorResponse = ApiResponse.<List<AccountResponse>>builder()
                            .success(false)
                            .message("Employee salary details not found")
                            .data(null)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build();

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                }
            }
        } catch (Exception ex) {
            ApiResponse<List<AccountResponse>> errorResponse = ApiResponse.<List<AccountResponse>>builder()
                    .success(false)
                    .message("An error occurred while fetching employee details")
                    .errors(ex.getMessage())
                    .data(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PutMapping("/update-amount")
    public ResponseEntity<ApiResponse<Void>> updateAmount(@RequestBody @Valid List<EmployeeSalary> employeeSalaries) {
        try {
            if (employeeSalaries.isEmpty()) {
                throw new IllegalArgumentException("No salary data found");
            }
            Employee currentEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            accountService.updateAmount(employeeSalaries, currentEmployee);

            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .success(true)
                    .message("Salary disbursement requests queued")
                    .data(null)
                    .statusCode(HttpStatus.ACCEPTED.value())
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

        } catch (Exception ex) {
            ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
                    .success(false)
                    .message("Failed to queue salary requests")
                    .errors(ex.getMessage())
                    .data(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PostMapping("/salary")
    public ResponseEntity<ApiResponse<Void>> setSalary(@RequestBody @Valid EmployeeSalary employeeSalary) {
        try {
            accountService.saveSalary(employeeSalary);

            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .success(true)
                    .message("Salary request queued for disbursement")
                    .data(null)
                    .statusCode(HttpStatus.ACCEPTED.value()) // better than 200 OK
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

        } catch (Exception ex) {
            ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
                    .success(false)
                    .message("Failed to queue salary request")
                    .errors(ex.getMessage())
                    .data(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}
