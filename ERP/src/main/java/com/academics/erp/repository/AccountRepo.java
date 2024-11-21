package com.academics.erp.repository;

import com.academics.erp.DTO.AccountResponse;
import com.academics.erp.entities.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<EmployeeSalary, Integer> {

    @Query("SELECT new com.academics.erp.DTO.AccountResponse(" +
            "e.employee_id, e.first_name, e.last_name, e.email, e.title, e.photograph_path, e.department, es) " +
            "FROM Employee e " +
            "LEFT JOIN EmployeeSalary es ON e.employee_id = es.employee_id")
    List<AccountResponse> findAllEmployeeDetails();

    @Query("SELECT es FROM EmployeeSalary es")
    List<EmployeeSalary> getAllEmployeeSalary();

}
