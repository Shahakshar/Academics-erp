package com.academics.erp.controllers;

import com.academics.erp.DTO.AccountResponse;
import com.academics.erp.DTO.ApiResponse;
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
            // Retrieve the currently authenticated employee
            Employee currentEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Update the salaries using the service layer
            accountService.updateAmount(employeeSalaries, currentEmployee);

            // Build the success response
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .success(true)
                    .message("Salaries updated successfully")
                    .data(null)
                    .statusCode(HttpStatus.OK.value())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // Handle exceptions and build the error response
            ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
                    .success(false)
                    .message("Failed to update salaries")
                    .errors(ex.getMessage())
                    .data(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
