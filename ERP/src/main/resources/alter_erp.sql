use ERP;

-- Add foreign key linking `department` in Employees to `department_id` in Departments.
ALTER TABLE Employees
    ADD CONSTRAINT fk_department FOREIGN KEY (department) REFERENCES Departments(department_id);

-- Add foreign key linking `employee_id` in Employee_Salary to `employee_id` in Employees.
ALTER TABLE Employee_Salary
    ADD CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES Employees(employee_id);