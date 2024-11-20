package com.academics.erp.controllers;

import com.academics.erp.DTO.LoginRequestObj;
import com.academics.erp.DTO.LoginResponseObj;
import com.academics.erp.DTO.RequestPasswordObj;
import com.academics.erp.entities.Employee;
import com.academics.erp.services.EmployeeService;
import com.academics.erp.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<List<Employee>> allEmployees() {
        return ResponseEntity.ok(employeeService.allEmployees());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseObj> loginEmployee(@RequestBody LoginRequestObj loginRequestObj) {

        Employee authenticateEmployee = employeeService.authenticate(loginRequestObj);
        String jwtToken =jwtService.generateToken(authenticateEmployee);

        LoginResponseObj loginResponse = new LoginResponseObj()
                                                .setToken(jwtToken)
                                                .setExpiry_time(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

}
