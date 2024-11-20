package com.academics.erp.repository;

import com.academics.erp.entities.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<EmployeeSalary, Integer> {



}
