import React, { useState, useEffect } from "react";
import axiosInstance from "../api/axiosInstance";
import "../styles/salaryModal.css"; // Add this CSS file for styling

const SalaryModal = ({ employee, closeModal }) => {
  const [salaryDetails, setSalaryDetails] = useState([]);

  useEffect(() => {
    // Fetch salary details
    const fetchSalaryDetails = async () => {
      try {
        const response = await axiosInstance.get(`/account/amount`, {
          requiresAuth: true,
        });
        setSalaryDetails(response.data.data);
      } catch (error) {
        console.error("Failed to fetch salary details:", error);
      }
    };
    fetchSalaryDetails();
  }, [employee.employee_id]);

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="close-button" onClick={closeModal}>
          &times;
        </button>
        <h3>
          Salary Details for {employee.first_name} {employee.last_name}
        </h3>
        <table className="salary-table">
          <thead>
            <tr>
              <th>Payment Date</th>
              <th>Amount</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
            {salaryDetails
              .filter((salary) => salary?.employeeSalary?.employee_id === employee.employee_id)
              .map((salary) => (
                <tr key={salary?.employeeSalary?.id}>
                  <td>
                    {new Date(
                      salary?.employeeSalary?.payment_date
                    ).toLocaleDateString()}
                  </td>
                  <td>{salary?.employeeSalary?.amount}</td>
                  <td>{salary?.employeeSalary?.description || "N/A"}</td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default SalaryModal;