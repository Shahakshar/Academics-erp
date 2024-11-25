import React, { useEffect, useState } from "react";
import axiosInstance from "../api/axiosInstance";
import "../styles/employeeTable.css";
import SalaryModal from "./salaryModal";

function EmployeeTable() {
  const [employees, setEmployees] = useState([]);
  const [selectedEmployees, setSelectedEmployees] = useState([]);
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [selectedEmployee, setSelectedEmployee] = useState(null);
   const [isModalOpen, setIsModalOpen] = useState(false);
  


  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const response = await axiosInstance.get("/account/amount", { requiresAuth: true });
        setEmployees(response.data.data);
      } catch (error) {
        console.error("Error fetching employees:", error);
      }
    };

    fetchEmployees();
  }, []);

  const handleViewSalary = (employee) => {
    console.log(employee)
    setSelectedEmployee(employee);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedEmployee(null);
  };
  
  const handleSelectAll = () => {
    setSelectedEmployees(employees);
  };

  const handleDeselectAll = () => {
    setSelectedEmployees([]);
  };

  const handleSendAmount = async () => {
    try {
      const payload = selectedEmployees.map((employee) => ({
        employee_id: employee.employee_id,
        amount,
        description,
        payment_date: new Date().toISOString(),
      }));

      await axiosInstance.put("/account/update-amount", payload, { requiresAuth: true });
      alert("Amount sent to selected employees successfully!");
    } catch (error) {
      console.error("Error sending amount:", error);
    }
  };

  return (
    <div style={{ margin: "20px" }}>
      <h2>Employee Information</h2>
      <button onClick={handleSelectAll} style={{ marginRight: "10px" }}>Select All</button>
      <button onClick={handleDeselectAll}>Deselect All</button>
      <table border="1" style={{ width: "100%", borderCollapse: "collapse", marginTop: "10px" }}>
        <thead>
          <tr>
            <th>Select</th>
            <th>Employee ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Title</th>
            <th>Photograph</th>
            <th>Department</th>
          </tr>
        </thead>
        <tbody>
          {employees.map((employee) => (
            <tr key={employee.employee_id}>
              <td>
                <input
                  type="checkbox"
                  checked={selectedEmployees.some((e) => e.employee_id === employee.employee_id)}
                  onChange={(e) => {
                    if (e.target.checked) {
                      setSelectedEmployees((prev) => [...prev, employee]);
                    } else {
                      setSelectedEmployees((prev) =>
                        prev.filter((selected) => selected.employee_id !== employee.employee_id)
                      );
                    }
                  }}
                />
              </td>
              <td>{employee.employee_id}</td>
              <td>{employee.first_name}</td>
              <td>{employee.last_name}</td>
              <td>{employee.email}</td>
              <td>{employee.title || "N/A"}</td>
              <td>
                {employee.photograph_path ? (
                  <img
                    src={employee.photograph_path}
                    alt={employee.first_name}
                    style={{ width: "50px", height: "50px", borderRadius: "5px" }}
                  />
                ) : (
                  "No Image"
                )}
              </td>
              <td>{employee.department?.name || "N/A"}</td>
              <td>
                 <button onClick={() => handleViewSalary(employee)}>View Salary</button>
               </td>
            </tr>
          ))}
        </tbody>
      </table>

      {selectedEmployees.length > 0 && (
        <>
          <h3>Selected Employees</h3>
          <table border="1" style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr>
                <th>Employee ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
              </tr>
            </thead>
            <tbody>
              {selectedEmployees.map((employee) => (
                <tr key={employee.employee_id}>
                  <td>{employee.employee_id}</td>
                  <td>{employee.first_name}</td>
                  <td>{employee.last_name}</td>
                  <td>{employee.email}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <div style={{ marginTop: "10px" }}>
            <label>
              Amount:
              <input
                type="number"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                style={{ marginLeft: "10px", marginRight: "10px" }}
              />
            </label>
            <label>
              Description:
              <input
                type="text"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                style={{ marginLeft: "10px" }}
              />
            </label>
            <button onClick={handleSendAmount} style={{ marginLeft: "10px" }}>
              Send Amount
            </button>
          </div>
        </>
      )}



<div style={{ margin: "20px" }}>
       {isModalOpen && (
         <SalaryModal
           employee={selectedEmployee}
           closeModal={closeModal}
         />
       )}
     </div>
    </div>

    
  );
}

export default EmployeeTable;