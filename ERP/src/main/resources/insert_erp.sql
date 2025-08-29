-- Use the ERP database
USE ERP;

-- Insert data into the Departments table
INSERT INTO Departments (name, capacity)
VALUES
    ('Academics', 500),
    ('Accounts', 50),
    ('Support', 15),
    ('Admin', 5);

-- Insert data into the Employees table
INSERT INTO Employees (first_name, last_name, email, title, photograph_path, department)
VALUES
    ('Mahesh', 'Patel', 'mahesh.patel@university.com', 'Professor', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Ramesh', 'Solanki', 'ramesh.solanki@university.com', 'Lecturer', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Jaya', 'Desai', 'jaya.desai@university.com', 'Senior Lecturer', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Naresh', 'Chauhan', 'naresh.chauhan@university.com', 'Research Associate', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Himanshu', 'Shah', 'himanshu.shah@university.com', 'Teaching Assistant', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Kiran', 'Makwana', 'kiran.makwana@university.com', 'Lab Coordinator', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Dipa', 'Mecha', 'dipa.mecha@university.com', 'Lecturer', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Hetal', 'Jadeja', 'hetal.jadeja@university.com', 'Professor', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Mayank', 'Singh', 'mayank.singh@university.com', 'Research Fellow', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Bhavna', 'Joshi', 'bhavna.joshi@university.com', 'Postdoc Researcher', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 1),
    ('Suresh', 'Vora', 'suresh.vora@university.com', 'Accountant', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 2),
    ('Nita', 'Pandya', 'nita.pandya@university.com', 'Accounts Manager', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 2),
    ('Ashok', 'Thakkar', 'ashok.thakkar@university.com', 'IT Technician', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 3),
    ('Meena', 'Patadia', 'meena.patadia@university.com', 'Support Executive', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 3),
    ('Rajesh', 'Modi', 'rajesh.modi@university.com', 'Admin Manager', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 4),
    ('Pooja', 'Bhatt', 'pooja.bhatt@university.com', 'Administrative Assistant', 'https://drive.google.com/file/d/1LSjfeVKpIt1U2B1OlCnmdZFsreDzoVxn/view?usp=drive_link', 4);

-- Insert data into the Employee_Salary table
INSERT INTO Employee_Salary (employee_id, payment_date, amount, description)
VALUES
    (1, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (2, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (3, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (4, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (5, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (6, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (7, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (8, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (9, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (10, CURRENT_DATE, 100000.00, 'Monthly salary for December 2024'),
    (11, CURRENT_DATE, 50000.00, 'Monthly salary for December 2024'),
    (12, CURRENT_DATE, 50000.00, 'Monthly salary for December 2024'),
    (13, CURRENT_DATE, 25000.00, 'Monthly salary for December 2024'),
    (14, CURRENT_DATE, 25000.00, 'Monthly salary for December 2024'),
    (15, CURRENT_DATE, 45000.00, 'Monthly salary for December 2024'),
    (16, CURRENT_DATE, 45000.00, 'Monthly salary for December 2024');
