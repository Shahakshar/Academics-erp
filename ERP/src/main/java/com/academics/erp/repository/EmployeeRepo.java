package com.academics.erp.repository;

import com.academics.erp.entities.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    @Transactional
    @Modifying
    @Query("Update Employee e set e.password = ?1 where e.employee_id between 1 and 16")
    void updatePassword(@Param("password") String password);

    Optional<Employee> findByEmail(String email);
}
