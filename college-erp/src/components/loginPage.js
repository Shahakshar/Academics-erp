import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance"; // Ensure this is correctly configured
import "../styles/loginPage.css";

const LoginPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault(); // Prevent form default submission behavior
        try {
            const response = await axiosInstance.post("/auth/login", {
                email,
                password,
            });

            // console.log("Login response:", response); // Debug response

            // Assuming your backend returns the token in response.data.token
            const token = response.data.token || response.data.data?.token;

            if (token) {
                localStorage.setItem("token", token); // Save token
                // console.log("Token saved:", token); // Debug token
                navigate("/dashboard"); // Redirect to Dashboard
            } else {
                setError("Login failed. Token not received.");
            }
        } catch (err) {
            console.error("Login error:", err.response || err.message); // Debug error
            setError(
                err.response?.data?.message || "Invalid email or password"
            );
        }
    };

    return (
        <div className="container">
            <div className="left-side"></div>
            <div className="login-container">
                <h2>Login</h2>
                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label>Email:</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>Password:</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    {error && <p className="error-message">{error}</p>}
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    );
};

export default LoginPage;