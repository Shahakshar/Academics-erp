import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';
import "../styles/setSalary.css";

function SetSalary() {
  const { state } = useLocation();
  const navigate = useNavigate();
  const token = state?.token;
  const employee = state?.employee;  // Get employee data passed from EmployeeTable

  // State to store the new salary input
  const [salary, setSalary] = useState('');
  const [description, setDescription] = useState('');  // New state for description
  const [error, setError] = useState('');
    
  // Handle salary submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!salary || isNaN(salary) || salary <= 0) {
      setError('Please enter a valid salary.');
      return;
    }

    if (!description) {
      setError('Please enter a description.');
      return;
    }

    const paymentDate = new Date().toISOString();  // Get current date in ISO format

    try {
      // Make the API call to update the employee's salary (assuming endpoint for salary exists)
      await axiosInstance.post(`/account/salary`, {
        employee_id: employee.employee_id,  // Use employee_id from the passed employee object
        payment_date: paymentDate,  // Send the current date as payment date
        amount: parseFloat(salary),  // Send salary amount
        description: description,  // Send the description
      }, { requiresAuth: true });

      alert('Salary updated successfully');
      localStorage.setItem("token",token)
      navigate('/dashboard');  // Navigate back to the dashboard after updating the salary
    } catch (error) {
      console.error('Error setting salary:', error);
      setError('Failed to update salary. Please try again.');
    }
  };


  useEffect(() => {
    // Save token to localStorage if not already stored
    if (token) {
      localStorage.setItem("token", token);
    } else {
      console.warn("No token found. Redirecting to login.");
      navigate("/"); // Redirect to login if token is missing
    }

    // Handle popstate event
    const handlePopState = () => {
      if (token) {
        localStorage.setItem("token", token); // Ensure token persists
        navigate("/dashboard");
      }
    };

    // Add event listener
    window.addEventListener("popstate", handlePopState);

    // Cleanup on unmount
    return () => {
      window.removeEventListener("popstate", handlePopState);
    };
  }, [navigate, token]);


  if (!employee) {
    return <div>No employee data found.</div>;
  }

  return (
    <div className="setsalary-container">
      {/* Employee Details Card */}
      <div className="setsalary-card">
        <h2>Employee Details</h2>
        
        <div className="setsalary-details">
          <div className="setsalary-details-row">
            <p><strong>Employee ID:</strong> {employee.employee_id}</p>
            <p><strong>Title:</strong> {employee.title}</p>
          </div>
          <div className="setsalary-details-row">
            <p><strong>First Name:</strong> {employee.first_name}</p>
            <p><strong>Last Name:</strong> {employee.last_name}</p>
          </div>
          <div className="setsalary-details-row">
            <p><strong>Email:</strong> {employee.email}</p>
            <p><strong>Department:</strong> {employee.department?.name || 'Unknown'}</p>
          </div>
          <div className="setsalary-details-row">
            <p><strong>Photograph:</strong></p>
            {employee.photograph_path ? (
              <img
                src={employee.photograph_path}
                alt={`${employee.first_name}'s Photograph`}
                style={{ width: '100px', height: '100px', borderRadius: '5px' }}
              />
            ) : (
              <span>No Image</span>
            )}
          </div>
        </div>
      </div>

      {/* Salary Input Card */}
      <div className="setsalary-card">
        <h3>Set Salary</h3>
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className="setsalary-input-group">
            <label>
              Salary:
              <input
                type="number"
                value={salary}
                onChange={(e) => setSalary(e.target.value)}
                placeholder="Enter salary"
                required
              />
            </label>
          </div>

          <div className="setsalary-input-group">
            <label>
              Description:
              <input
                type="text"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                placeholder="Enter description"
                required
              />
            </label>
          </div>

          <button type="submit" className="setsalary-submit-btn">
            Submit Salary
          </button>
        </form>
      </div>
    </div>
  );
}

export default SetSalary;