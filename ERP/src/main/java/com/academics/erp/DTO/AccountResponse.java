package com.academics.erp.dto;


import com.academics.erp.entities.Department;
import com.academics.erp.entities.EmployeeSalary;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {

    /*
        not Using Employee object directly instead use some of required properties
    */

    private int employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private String title;
    private String photograph_path;
    private Department department;
    private EmployeeSalary employeeSalary;

    public AccountResponse(EmployeeSalary employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public AccountResponse() {
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhotograph_path(String photograph_path) {
        this.photograph_path = photograph_path;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public AccountResponse(int employee_id, String first_name, String last_name, String email, String title, String photograph_path, Department department, EmployeeSalary employeeSalary) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.title = title;
        this.photograph_path = photograph_path;
        this.department = department;
        this.employeeSalary = employeeSalary;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "employee_id=" + employee_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", photograph_path='" + photograph_path + '\'' +
                ", department=" + department +
                ", employeeSalary=" + employeeSalary +
                '}';
    }
}
