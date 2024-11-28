package com.academics.erp.services;

import com.academics.erp.dto.LoginRequestObj;
import com.academics.erp.dto.RequestPasswordObj;
import com.academics.erp.entities.Employee;
import com.academics.erp.repository.EmployeeRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public EmployeeService(EmployeeRepo employeeRepo, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void updateEmployeePasswords(RequestPasswordObj passwordObj) {
        employeeRepo.updatePassword(passwordEncoder.encode(passwordObj.getPassword()));
    }

    public List<Employee> allEmployees() {
        return employeeRepo.findAll();
    }

    public Employee authenticate(LoginRequestObj loginRequestObj) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestObj.getEmail(),
                        loginRequestObj.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return employeeRepo.findByEmail(loginRequestObj.getEmail())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }
}
