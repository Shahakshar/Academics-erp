package com.academics.erp.controllers;

import com.academics.erp.DTO.ApiResponse;
import com.academics.erp.DTO.LoginRequestObj;
import com.academics.erp.DTO.LoginResponseObj;
import com.academics.erp.DTO.RequestPasswordObj;
import com.academics.erp.entities.Employee;
import com.academics.erp.services.EmployeeService;
import com.academics.erp.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtService jwtService;

    public EmployeeController(EmployeeService employeeService, JwtService jwtService) {
        this.employeeService = employeeService;
        this.jwtService = jwtService;
    }

    @PutMapping("/update-password")
    public String updatePassword(@RequestBody RequestPasswordObj passwordObj) {
        employeeService.updateEmployeePasswords(passwordObj);
        return "Password updated successfully";
    }

    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<List<Employee>>> allEmployees() {
        try {
            List<Employee> employees = employeeService.allEmployees();

            ApiResponse<List<Employee>> response = ApiResponse.<List<Employee>>builder()
                    .success(true)
                    .message("Employees fetched successfully")
                    .data(employees)
                    .statusCode(HttpStatus.OK.value())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ApiResponse<List<Employee>> errorResponse = ApiResponse.<List<Employee>>builder()
                    .success(false)
                    .message("Failed to fetch employees")
                    .errors(ex.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } finally {
            System.out.println("Employee list fetched");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseObj>> loginEmployee(@RequestBody LoginRequestObj loginRequestObj) {
        try {
            // Authenticate employee
            Employee authenticateEmployee = employeeService.authenticate(loginRequestObj);
            // Generate JWT token
            String jwtToken = jwtService.generateToken(authenticateEmployee);

            LoginResponseObj loginResponse = new LoginResponseObj()
                    .setToken(jwtToken)
                    .setExpiry_time(jwtService.getExpirationTime());

            ApiResponse<LoginResponseObj> response = ApiResponse.<LoginResponseObj>builder()
                    .success(true)
                    .message("Login successful")
                    .data(loginResponse)
                    .statusCode(HttpStatus.OK.value())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // Handle errors and create error response
            ApiResponse<LoginResponseObj> errorResponse = ApiResponse.<LoginResponseObj>builder()
                    .success(false)
                    .message("Login failed")
                    .errors(ex.getMessage())
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } finally {
            System.out.println("Employee logged in");
        }
    }


}
