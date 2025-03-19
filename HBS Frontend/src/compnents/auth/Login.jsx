import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [formData, setFormData] = useState({
    role: "",
    usernameOrName: "",
    password: "",
  });
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate(); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const validateFields = () => {
    if (!formData.role) {
      return "Please select a role (Admin or User)";
    }
    if (formData.usernameOrName === "") {
      return formData.role === "Admin" ? "Name is required" : "Username is required";
    }
    if (formData.password === "") {
      return "Password is required";
    }
    return "";
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const errorMsg = validateFields();
    if (errorMsg) {
      setError(errorMsg);
      return;
    }

    setError("");

    try {
      const endpoint =
        formData.role === "User"
          ? "http://localhost:9999/api/customers/login"
          : "http://localhost:9999/api/admins/login";

      const payload =
        formData.role === "User"
          ? {
              username: formData.usernameOrName,
              password: formData.password,
            }
          : {
              name: formData.usernameOrName,
              password: formData.password,
            };

      const response = await axios.post(endpoint, payload, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.data.success || response.status === 200) {
        if (formData.role === "User") {
          setSuccessMessage("Login successful! Redirecting to the home page...");
          setTimeout(() => navigate("/home"), 2000);
        } else if (formData.role === "Admin") {
          setSuccessMessage("Login successful!");
          setShowModal(true);
        }
      }
    } catch (error) {
      console.error(
        "Login error:",
        error.response ? error.response.data : error.message
      );
      setError("Login failed. Please check your credentials and try again.");
    }
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const navigateTo = (path) => {
    setShowModal(false);
    navigate(path); 
  };

  return (
    <div className="container mt-5">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <select
            className="form-control"
            id="role"
            name="role"
            value={formData.role}
            onChange={handleChange}
            required
          >
            <option value="" disabled>
              Select Role
            </option>
            <option value="User">User</option>
            <option value="Admin">Admin</option>
          </select>
        </div>
        <div className="mb-3">
          <input
            type="text"
            className="form-control"
            id="usernameOrName"
            name="usernameOrName"
            placeholder={
              formData.role === "Admin"
                ? "Enter Name"
                : "Enter Username"
            }
            value={formData.usernameOrName}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <div className="input-group">
            <input
              type={showPassword ? "text" : "password"}
              className="form-control"
              id="password"
              name="password"
              placeholder="Enter Password"
              value={formData.password}
              onChange={handleChange}
              required
            />
            <button
              type="button"
              className="btn btn-outline-secondary"
              onClick={togglePasswordVisibility}
            >
              {showPassword ? "Hide" : "Show"}
            </button>
          </div>
        </div>
        {error && <div className="alert alert-danger">{error}</div>}
        {successMessage && <div className="alert alert-success">{successMessage}</div>}
        <button type="submit" className="btn btn-primary">
          Login
        </button>
      </form>

      {/* Bootstrap Modal */}
      {showModal && (
        <div
          className="modal show fade"
          style={{ display: "block" }}
          tabIndex="-1"
          role="dialog"
        >
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header bg-primary text-white">
                <h5 className="modal-title">Welcome Admin</h5>
                <button
                  type="button"
                  className="btn-close"
                  aria-label="Close"
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body text-center">
                <p>Where would you like to navigate?</p>
                <button
                  className="btn btn-success me-3"
                  onClick={() => navigateTo("/home")}
                >
                  Hotel Page
                </button>
                <button
                  className="btn btn-primary"
                  onClick={() => navigateTo("/admin")}
                >
                  Admin Panel
                </button>
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => setShowModal(false)}
                >
                  Close
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Login;
