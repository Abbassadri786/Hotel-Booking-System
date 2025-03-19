import React from "react";
import { Link } from "react-router-dom";

const Admin = () => {
  return (
    <section className="container mt-5">
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
