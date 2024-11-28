package com.academics.erp.repository;

import com.academics.erp.dto.AccountResponse;
import com.academics.erp.entities.EmployeeSalary;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<EmployeeSalary, Integer> {

    @Query("SELECT new com.academics.erp.dto.AccountResponse(" +
            "e.employee_id, e.first_name, e.last_name, e.email, e.title, e.photograph_path, e.department, es) " +
            "FROM Employee e " +
            "LEFT JOIN EmployeeSalary es ON e.employee_id = es.employee_id")
    List<AccountResponse> findAllEmployeeDetails();

    @Query("SELECT es FROM EmployeeSalary es")
    List<EmployeeSalary> getAllEmployeeSalary();

    @Transactional
    @Modifying
    @Query("UPDATE EmployeeSalary e SET e.amount = ?1, e.description = ?2, e.payment_date = ?3 WHERE e.employee_id = ?4")
    void updateSalaryOfEmployee(Double amount, String description, Date paymentDate, Integer employeeId);

}
