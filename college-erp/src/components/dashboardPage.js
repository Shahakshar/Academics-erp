import React, {useEffect} from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "./navbar";
import EmployeeTable from "./employeeTable";
import '../styles/dashboardPage.css';


const DashboardPage = () => {
    const navigate = useNavigate();

    useEffect(() => {
      // Replace history state to ensure user can't navigate back to login
      window.history.replaceState(null, null, window.location.href);
  
      const handlePopState = () => {
        // Check if the token exists in localStorage
        const token = localStorage.getItem("token");
        if (!token) {
          // Redirect to login if token is missing
          navigate("/");
        }
      };
  
      // Attach popstate listener
      window.addEventListener("popstate", handlePopState);
  
      // Cleanup on unmount
      return () => {
        window.removeEventListener("popstate", handlePopState);
      };
    }, [navigate]);
    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/");
        // window.location.reload(); // Reload to clear cached content
      };

    return (
        <div className="dashboard-container">
            <Navbar onLogout={handleLogout} />
            <EmployeeTable token={localStorage.getItem('token')} />
        </div>
    );
};

export default DashboardPage;