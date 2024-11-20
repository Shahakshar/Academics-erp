package com.academics.erp.DTO;


import com.academics.erp.entities.Employee;
import com.academics.erp.entities.EmployeeSalary;

public class AccountResponse {

    /*
        not Using Employee object directly instead use some of required properties
    */

    private Employee employee;
    private EmployeeSalary employeeSalary;

    public AccountResponse(Employee employee, EmployeeSalary employeeSalary) {
        this.employee = employee;
        this.employeeSalary = employeeSalary;
    }

    public AccountResponse() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public AccountResponse setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public EmployeeSalary getEmployeeSalary() {
        return employeeSalary;
    }

    public AccountResponse setEmployeeSalary(EmployeeSalary employeeSalary) {
        this.employeeSalary = employeeSalary;
        return this;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "employee=" + employee +
                ", employeeSalary=" + employeeSalary +
                '}';
    }
}
