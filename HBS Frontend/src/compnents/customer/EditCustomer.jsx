import React, { useEffect, useState } from "react";
import { getCustomerById, updateCustomer } from "../utils/ApiFunctions"; // API functions for customers
import { useParams, useNavigate } from "react-router-dom";

const EditCustomer = () => {
  const [customer, setCustomer] = useState({
    username: "",
    email: "",
    phone: "",
    gender: "",
    dob: "",
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCustomer((prevCustomer) => ({
      ...prevCustomer,
      [name]: value,
    }));
  };

  useEffect(() => {
    const fetchCustomer = async () => {
      try {
        const customerData = await getCustomerById(id);
        setCustomer(customerData);
      } catch (error) {
        console.error("Error fetching customer:", error);
        setErrorMessage("Error fetching customer details.");
      }
    };
    fetchCustomer();
  }, [id]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await updateCustomer(id, customer);
      if (response) {
        setSuccessMessage("Customer updated successfully.");
        setErrorMessage("");
      } else {
        setErrorMessage("Error updating customer.");
      }
    } catch (error) {
      console.error("Error updating customer:", error);
      setErrorMessage(error.message);
    }
  };

  const handleBack = () => {
    navigate("/existing-customers"); // Back to the customer list
  };

  return (
    <section className="container mt-5 mb-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <h2 className="mt-5 mb-2">Edit Customer</h2>
          {successMessage && (
            <div className="alert alert-success fade show">{successMessage}</div>
          )}
          {errorMessage && (
            <div className="alert alert-danger fade show">{errorMessage}</div>
          )}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="username" className="form-label">Username</label>
              <input
                className="form-control"
                required
                id="username"
                name="username"
                type="text"
                value={customer.username}
                onChange={handleInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="email" className="form-label">Email</label>
              <input
                className="form-control"
                required
                id="email"
                name="email"
                type="email"
                value={customer.email}
                onChange={handleInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="phone" className="form-label">Phone</label>
              <input
                className="form-control"
                required
                id="phone"
                name="phone"
                type="text"
                value={customer.phone}
                onChange={handleInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="gender" className="form-label">Gender</label>
              <select
                className="form-control"
                required
                id="gender"
                name="gender"
                value={customer.gender}
                onChange={handleInputChange}
              >
                <option value="" disabled>Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
              </select>
            </div>

            <div className="mb-3">
              <label htmlFor="dob" className="form-label">Date of Birth</label>
              <input
                className="form-control"
                required
                id="dob"
                name="dob"
                type="date"
                value={customer.dob}
                onChange={handleInputChange}
              />
            </div>

            <div className="d-grid justify-content-between d-md-flex mt-2">
              <button type="button" className="btn btn-outline-primary ml-5" onClick={handleBack}>
                Back
              </button>
              <button className="btn btn-outline-primary ml-5">Edit Customer</button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};

export default EditCustomer;
