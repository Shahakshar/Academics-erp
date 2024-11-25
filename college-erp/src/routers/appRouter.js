import React from "react";
import { Route, Routes } from "react-router-dom";
import LoginPage from "../components/loginPage";
import DashboardPage from "../components/dashboardPage";
import ProtectedRoute from "./protectedRoute";

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<LoginPage />} />
            {/* Protect the dashboard route */}
            <Route
                path="/dashboard"
                element={
                    <ProtectedRoute>
                        <DashboardPage />
                    </ProtectedRoute>
                }
            />
        </Routes>
    );
};

export default AppRoutes;
