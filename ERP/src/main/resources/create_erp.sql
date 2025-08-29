-- Create the ERP database
CREATE DATABASE IF NOT EXISTS ERP;

-- Use the ERP database
USE ERP;

-- Create the Departments table
CREATE TABLE Departments
(
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    capacity      INT          NOT NULL
);

-- Create the Employees table
CREATE TABLE Employees
(
    employee_id     INT AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(255)        NOT NULL,
    last_name       VARCHAR(255)        NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    title           VARCHAR(100),
    photograph_path VARCHAR(255),
    department      INT,
    FOREIGN KEY (department) REFERENCES Departments (department_id)
);

-- Create the Employee_Salary table
CREATE TABLE Employee_Salary
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    employee_id  INT            NOT NULL,
    payment_date DATE           NOT NULL,
    amount       DECIMAL(10, 2) NOT NULL,
    description  VARCHAR(100),
    FOREIGN KEY (employee_id) REFERENCES Employees (employee_id)
);
