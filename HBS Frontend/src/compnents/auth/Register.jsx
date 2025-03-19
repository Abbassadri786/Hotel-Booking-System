import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const [formData, setFormData] = useState({
    role: "",
    username: "",
    name: "",
    email: "",
    password: "",
    phone: "",
    mobile: "",
    gender: "",
    dob: "",
    accessCode: "",
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const navigate = useNavigate(); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const validateFields = () => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const phoneRegex = /^[0-9]{10}$/;

    if (!emailRegex.test(formData.email)) {
      return "Invalid email address";
    }

    if (
      (formData.role === "Admin" || formData.role === "User") &&
      !phoneRegex.test(formData.mobile || formData.phone)
    ) {
      return "Phone number must be exactly 10 digits";
    }

    return "";
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const errorMsg = validateFields();
    if (errorMsg) {
      setError(errorMsg);
      setSuccess("");
      return;
    }

    const requiredFields =
      formData.role === "User"
        ? ["username", "email", "password", "gender"]
        : ["name", "email", "password", "mobile", "accessCode"];

    for (const field of requiredFields) {
      if (formData[field] === "") {
        setError("All required fields must be filled out");
        setSuccess("");
        return;
      }
    }

    if (formData.role === "Admin" && formData.accessCode !== "admin@1234") {
      setError("Wrong access code entered");
      setSuccess("");
      return;
    }

    setError("");

    try {
      const endpoint =
        formData.role === "User"
          ? "http://localhost:9999/api/customers"
          : "http://localhost:9999/api/admins/register";

      const payload =
        formData.role === "User"
          ? {
              username: formData.username,
              email: formData.email,
              password: formData.password,
              gender: formData.gender,
              ...(formData.phone && { phone: formData.phone }),
              ...(formData.dob && { dob: formData.dob }),
            }
          : {
              accessCode: formData.accessCode,
              admin: {
                name: formData.name,
                email: formData.email,
                mobile: formData.mobile,
                password: formData.password,
              },
            };

      const response = await axios.post(endpoint, payload, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.status === 200) {
        setSuccess("Registration successful!");
        setError("");
        setTimeout(() => {
          navigate("/login");
        }, 2000); 
      }
    } catch (error) {
      console.error(
        "Error registering user/admin:",
        error.response ? error.response.data : error.message
      );
      setError("Registration failed. Please try again.");
      setSuccess("");
    }
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div className="container mt-5">
      <h2>Register</h2>
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

        {formData.role === "User" && (
          <>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                id="username"
                name="username"
                placeholder="Username (max 30 characters)"
                maxLength="30"
                value={formData.username}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <select
                className="form-control"
                id="gender"
                name="gender"
                value={formData.gender}
                onChange={handleChange}
                required
              >
                <option value="" disabled>
                  Select Gender
                </option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
              </select>
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                id="phone"
                name="phone"
                placeholder="Phone (10 digits, optional)"
                value={formData.phone}
                onChange={handleChange}
              />
            </div>
            <div className="mb-3">
              <input
                type="date"
                className="form-control"
                id="dob"
                name="dob"
                placeholder="Date of Birth (optional)"
                value={formData.dob}
                onChange={handleChange}
              />
            </div>
          </>
        )}

        {formData.role === "Admin" && (
          <>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                id="name"
                name="name"
                placeholder="Name (max 30 characters)"
                maxLength="30"
                value={formData.name}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                id="mobile"
                name="mobile"
                placeholder="Mobile (10 digits)"
                value={formData.mobile}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                id="accessCode"
                name="accessCode"
                placeholder="Access Code"
                value={formData.accessCode}
                onChange={handleChange}
                required
              />
            </div>
          </>
        )}

        <div className="mb-3">
          <input
            type="email"
            className="form-control"
            id="email"
            name="email"
            placeholder="Email"
            value={formData.email}
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
              placeholder="Password"
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
        {success && <div className="alert alert-success">{success}</div>}
        <button type="submit" className="btn btn-primary">
          Register
        </button>
      </form>
    </div>
  );
};

export default Register;
