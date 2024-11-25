import React from "react";

const Navbar = ({ onLogout }) => {
    return (
        <nav className="navbar">
            <h2>Academics-ERP</h2>
            <button onClick={onLogout} className="logout-button">Logout</button>
        </nav>
    );
};

export default Navbar;
