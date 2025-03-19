import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { FaArrowLeft } from "react-icons/fa";

const Admin = () => {
  const navigate = useNavigate();

  return (
    <section className="container mt-5">
      {/* Back Arrow Button */}
      <div className="mb-3">
        <button
          className="btn btn-link text-decoration-none text-dark"
          onClick={() => navigate("/home")}
          style={{
            display: "flex",
            alignItems: "center",
            gap: "5px",
            fontSize: "16px",
          }}
        >
          <FaArrowLeft /> Back to Home
        </button>
      </div>

      {/* Admin Panel Content */}
      <div className="card shadow-lg">
        <div className="card-header bg-primary text-white text-center">
          <h2>Admin Panel</h2>
        </div>
        <div className="card-body text-center">
          <p className="mb-4">Manage your operations efficiently with the options below:</p>
          <div className="d-grid gap-3">
            <Link to="/existing-rooms" className="btn btn-outline-primary btn-lg">
              Manage Rooms
            </Link>
            <Link to="/existing-bookings" className="btn btn-outline-success btn-lg">
              Manage Bookings
            </Link>
          </div>
        </div>
        <div className="card-footer text-muted text-center">
          <small>⚠️ Exclusive access for hotel staff only</small>
        </div>
      </div>
    </section>
  );
};

export default Admin;
