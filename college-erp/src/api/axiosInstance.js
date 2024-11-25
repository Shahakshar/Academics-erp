import axios from "axios";

// Create Axios instance
const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL || "http://localhost:8005",
    headers: {
        "Content-Type": "application/json",
    },
});

// // Add a request interceptor to include the token in headers
// axiosInstance.interceptors.request.use((config) => {
//     const token = localStorage.getItem("token");
//     if (token) {
//         config.headers.Authorization = `Bearer ${token}`;
//     }
//     return config;
// });

axiosInstance.interceptors.request.use(
    (config) => {
      // Check if the endpoint requires authentication
      if (config.requiresAuth) {
        const token = localStorage.getItem("token"); // Get the token from localStorage
        if (token) {
          config.headers["Authorization"] = `Bearer ${token}`; // Add token to Authorization header
        } else {
          console.warn("No token found for authenticated request.");
        }
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

export default axiosInstance;
