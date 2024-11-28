import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";  // Import useNavigate hook
import axiosInstance from "../api/axiosInstance";
import "../styles/employeeTable.css";
import SalaryModal from "./salaryModal";

function EmployeeTable({token}) {
  const navigate = useNavigate();  // Initialize useNavigate hook
  const [employees, setEmployees] = useState([]);
  const [selectedEmployees, setSelectedEmployees] = useState([]);
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isAccountingDepartment, setIsAccountingDepartment] = useState(false);
  const [departments, setDepartments] = useState([]);
  const [selectedDepartment, setSelectedDepartment] = useState("");
  const [loggedInUser, setLoggedInUser] = useState(null);


  useEffect(() => {
    // This effect handles listening to back button click (popstate event)
    const handlePopState = () => {
      // Clear the token when the back button is clicked
      localStorage.removeItem("token");
      alert("You have been logged out due to session expiration.");
      navigate("/login");  // Redirect to login page
    };

    // Listen for popstate event (back button click)
    window.addEventListener("popstate", handlePopState);

    // Cleanup the event listener when the component is unmounted
    return () => {
      window.removeEventListener("popstate", handlePopState);
    };
  }, [navigate]);


  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axiosInstance.get("/account/amount", { requiresAuth: true });
        const fetchedEmployees = response.data.data;
        setEmployees(fetchedEmployees);

        // Extract unique departments
        const uniqueDepartments = [
          ...new Set(fetchedEmployees.map((emp) => emp.department?.name || "Unknown")),
        ];
        setDepartments(uniqueDepartments);

        setIsAccountingDepartment(fetchedEmployees.length > 1);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  const handleSetSalary = (employee) => {
    // Instead of opening a modal, navigate to the 'set-salary' route
    navigate(`/dashboard/set-salary`, { state: { employee, token } });  // Pass employee data via state
  };

  const handleViewSalary = (employee) => {
    setSelectedEmployee(employee);
    setIsModalOpen(true);
  };


  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedEmployee(null);
  };

  const handleSelectAll = () => {
    setSelectedEmployees(filteredEmployees);
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
      setAmount("");     
      setDescription("")
    } catch (error) {
      console.error("Error sending amount:", error);
    }
  };

  

  


  // Filter employees based on the selected department
  const filteredEmployees = selectedDepartment
    ? employees.filter((emp) => emp.department?.name === selectedDepartment)
    : employees;

  return (
    <div style={{ margin: "20px" }}>
      <h2>Employee Information</h2>
      {isAccountingDepartment && (
        <div>
          <button onClick={handleSelectAll} style={{ marginRight: "10px" }}>
            Select All
          </button>
          <button onClick={handleDeselectAll}>Deselect All</button>
        </div>
      )}
      <div style={{ marginTop: "10px" }}>
        {/* Department Filter */}
        <label>
          Filter by Department:
          <select
            value={selectedDepartment}
            onChange={(e) => {
              setSelectedDepartment(e.target.value);
              handleDeselectAll();
            }}
            style={{ marginLeft: "10px" }}
          >
            <option value="">All Departments</option>
            {departments.map((dept) => (
              <option key={dept} value={dept}>
                {dept}
              </option>
            ))}
          </select>
        </label>
      </div>
      <table border="1" style={{ width: "100%", borderCollapse: "collapse", marginTop: "10px" }}>
        <thead>
          <tr>
            {isAccountingDepartment && <th>Select</th>}
            <th>Employee ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Title</th>
            <th>Photograph</th>
            <th>Department</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredEmployees.map((employee) => (
            <tr key={employee.employee_id} >
              {isAccountingDepartment && (
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
              )}
              <td onClick={() => handleSetSalary(employee)}>{employee.employee_id}</td>
              <td>{employee.first_name}</td>
              <td>{employee.last_name}</td>
              <td>{employee.email}</td>
              <td>{employee.title || "N/A"}</td>
              <td>
                {employee.photograph_path ? (
                  <img
                    src={employee.photograph_path}
                    alt={`${employee.first_name}`}
                    style={{ width: "50px", height: "50px", borderRadius: "5px" }}
                  />
                ) : (
                  "No Image"
                )}
              </td>
              <td>{employee.department?.name || "Unknown"}</td>
              <td>
              <button style={{ zIndex: 2 }} onClick={() => handleViewSalary(employee)}>View Amount</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {isAccountingDepartment && selectedEmployees.length > 0 && (
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
          <div className="inline-container">
            <input
              type="number"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              className="input-inline amount-input"
              placeholder="Amount"
            />
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="input-inline description-input"
              placeholder="Description"
            ></textarea>
            <button onClick={handleSendAmount} className="send-amount-btn">
              Send Amount
            </button>
          </div>
        </>
      )}

      {isModalOpen && (
        <SalaryModal employee={selectedEmployee} closeModal={closeModal} />
      )}
    </div>
  );
}

export default EmployeeTable;