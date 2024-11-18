package com.academics.erp.services;

import com.academics.erp.DTO.RequestPasswordObj;
import com.academics.erp.repository.EmployeeRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepo employeeRepo, BCryptPasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void updateEmployeePasswords(RequestPasswordObj passwordObj) {
        employeeRepo.updatePassword(passwordEncoder.encode(passwordObj.getPassword()));
    }
}
