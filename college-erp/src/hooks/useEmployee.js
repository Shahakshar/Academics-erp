import { useEffect, useState } from "react";
import axiosInstance from "../api/axiosInstance";
import Employee from "../model/Employee";

const useEmployees = (token) => {
  const [employees, setEmployees] = useState([]);
  const [selectedEmployees, setSelectedEmployees] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [isAccountingDepartment, setIsAccountingDepartment] = useState(false);
  const [selectedDepartment, setSelectedDepartment] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axiosInstance.get("/account/amount", { headers: { Authorization: `Bearer ${token}` } });
        const fetchedEmployees = response.data.data.map(emp => new Employee(emp.employee_id, emp.first_name, emp.last_name, emp.email, emp.title, emp.photograph_path, emp.department));
        setEmployees(fetchedEmployees);

        // Extract unique departments
        const uniqueDepartments = [
          ...new Set(fetchedEmployees.map(emp => emp.department?.name || "Unknown")),
        ];
        setDepartments(uniqueDepartments);

        setIsAccountingDepartment(fetchedEmployees.length > 1);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [token]);

  return {
    employees,
    selectedEmployees,
    setSelectedEmployees,
    departments,
    isAccountingDepartment,
    selectedDepartment,
    setSelectedDepartment
  };
};

export default useEmployees;