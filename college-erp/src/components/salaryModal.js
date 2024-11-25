import React, { useState, useEffect } from "react";
import axiosInstance from "../api/axiosInstance";

const SalaryModal = ({ employee, closeModal }) => {
  const [salaryDetails, setSalaryDetails] = useState([]);
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    // Fetch salary details
    const fetchSalaryDetails = async () => {
      try {
        const response = await axiosInstance.get(`/account/amount`, {requiresAuth: true});
        console.log(response.data.data);
        setSalaryDetails(response.data.data);
      } catch (error) {
        console.error("Failed to fetch salary details:", error);
      }
    };
    fetchSalaryDetails();
  }, [employee.employee_id]);

  const handleDisburseSalary = async () => {
    try {
        const payload = [
            {
                employee_id: employee.employee_id,
                amount,
                description,
                payment_date: new Date().toISOString(),
            },
        ];
        await axiosInstance.put("/account/update-amount", payload, {requiresAuth: true});
        alert("Salary disbursed successfully!");
        closeModal();
        } catch (error) {
        console.error("Failed to disburse salary:", error);
        }
  };

  return (
    <div className="modal">
      <div className="modal-content">
        <span className="close" onClick={closeModal}>
          &times;
        </span>
        <h3>Salary Details for {employee.first_name} {employee.last_name}</h3>
        <table border="1" style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr>
              <th>Payment Date</th>
              <th>Amount</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
            {salaryDetails
              // Validate structure and filter based on employee_id
              .filter((salary) => salary?.employeeSalary?.employee_id === employee.employee_id)
              .map((salary) => (
                <tr key={salary?.employeeSalary?.id}>
                  <td>{new Date(salary?.employeeSalary?.payment_date).toLocaleDateString()}</td>
                  <td>{salary?.employeeSalary?.amount}</td>
                  <td>{salary?.employeeSalary?.description || "N/A"}</td>
                </tr>
              ))}
          </tbody>

        </table>
        <h4>Disburse Salary</h4>
        <div>
          <label>
            Amount:
            <input
              type="number"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
            />
          </label>
        </div>
        <div>
          <label>
            Description:
            <input
              type="text"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </label>
        </div>
        <button onClick={handleDisburseSalary}>Disburse</button>
      </div>
    </div>
  );
};

export default SalaryModal;