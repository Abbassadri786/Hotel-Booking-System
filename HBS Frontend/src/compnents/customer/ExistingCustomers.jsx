import React, { useEffect, useState } from "react";
import { deleteCustomer, getAllCustomers, searchCustomerByUsername } from "../utils/ApiFunctions"; // Import API functions
import "bootstrap/dist/css/bootstrap.min.css";
import { Col, Row } from "react-bootstrap";
import { FaTrashAlt, FaEdit, FaSearch, FaArrowLeft, FaTimes } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";

const ExistingCustomers = () => {
  const [customers, setCustomers] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [customersPerPage, setCustomersPerPage] = useState(8);
  const [isLoading, setIsLoading] = useState(false);
  const [filteredCustomers, setFilteredCustomers] = useState([]);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [selectedCustomerId, setSelectedCustomerId] = useState(null);
  const [searchQuery, setSearchQuery] = useState(""); // Search query for username
  const navigate = useNavigate();

  useEffect(() => {
    fetchCustomers();
  }, []);

  const fetchCustomers = async () => {
    setIsLoading(true);
    try {
      const result = await getAllCustomers();
      if (Array.isArray(result)) {
        setCustomers(result);
        setFilteredCustomers(result);
      } else {
        throw new Error("API did not return an array of customers");
      }
    } catch (error) {
      setErrorMessage(`Error fetching customers: ${error.message}`);
    } finally {
      setIsLoading(false);
    }
  };

  const handleSearch = async () => {
    if (searchQuery.trim() === "") {
      setFilteredCustomers(customers); // Reset to all customers if search is empty
      return;
    }
    try {
      const result = await searchCustomerByUsername(searchQuery);
      setFilteredCustomers([result]);
    } catch (error) {
      setErrorMessage(`Error searching customer: ${error.message}`);
    }
  };

  const clearSearch = () => {
    setSearchQuery(""); // Clear the search query
    setFilteredCustomers(customers); // Reset to all customers
    setErrorMessage(""); // Clear error messages related to search
  };

  const handlePaginationClick = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const openModal = (id) => {
    setSelectedCustomerId(id);
    setShowModal(true);
  };

  const handleDeleteConfirm = async () => {
    try {
      await deleteCustomer(selectedCustomerId);
      setSuccessMessage(`Customer with ID ${selectedCustomerId} was deleted successfully.`);
      fetchCustomers();
      setShowModal(false);
    } catch (error) {
      setErrorMessage(`Error deleting customer: ${error.message}`);
    } finally {
      setTimeout(() => {
        setSuccessMessage("");
        setErrorMessage("");
      }, 3000);
    }
  };

  const calculateTotalPages = (filteredCustomers, customersPerPage) => {
    const totalCustomers = filteredCustomers.length > 0 ? filteredCustomers.length : customers.length;
    return Math.ceil(totalCustomers / customersPerPage);
  };

  const indexOfLastCustomer = currentPage * customersPerPage;
  const indexOfFirstCustomer = indexOfLastCustomer - customersPerPage;
  const currentCustomers = filteredCustomers.slice(indexOfFirstCustomer, indexOfLastCustomer);

  return (
    <>
      {/* Back Arrow Button */}
      <div className="container mt-3">
        <button
          className="btn btn-link text-decoration-none text-dark"
          onClick={() => navigate("/admin")}
          style={{
            display: "flex",
            alignItems: "center",
            gap: "5px",
            fontSize: "16px",
          }}
        >
          <FaArrowLeft /> Back to Admin
        </button>
      </div>

      <div className="container col-md-8 col-lg-6">
        {successMessage && <p className="alert alert-success mt-5">{successMessage}</p>}
        {errorMessage && <p className="alert alert-danger mt-5">{errorMessage}</p>}
      </div>
      {isLoading ? (
        <p>Loading existing customers...</p>
      ) : errorMessage ? (
        <p>Error: {errorMessage}</p>
      ) : (
        <>
          <section className="mt-5 mb-5 container">
            <div className="d-flex justify-content-between mb-3 mt-5">
              <h2>Existing Customers</h2>
              <div className="d-flex gap-2">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Search by username"
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                />
                <button className="btn btn-primary" onClick={handleSearch}>
                  <FaSearch /> Search
                </button>
                {searchQuery && (
                  <button className="btn btn-secondary" onClick={clearSearch}>
                    <FaTimes /> Clear
                  </button>
                )}
              </div>
            </div>

            <table className="table table-bordered table-hover">
              <thead>
                <tr className="text-center">
                  <th>ID</th>
                  <th>Username</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Gender</th>
                  <th>Date of Birth</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {currentCustomers.map((customer) => (
                  <tr key={customer.id} className="text-center">
                    <td>{customer.id}</td>
                    <td>{customer.username}</td>
                    <td>{customer.email}</td>
                    <td>{customer.phone}</td>
                    <td>{customer.gender}</td>
                    <td>{customer.dob}</td>
                    <td className="gap-2">
                      <Link to={`/edit-customer/${customer.id}`}>
                        <span className="btn btn-warning btn-sm">
                          <FaEdit />
                        </span>
                      </Link>
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => openModal(customer.id)}
                      >
                        <FaTrashAlt />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </section>
        </>
      )}

      {/* Bootstrap Modal for Deletion Confirmation */}
      {showModal && (
        <div
          className="modal show fade"
          style={{ display: "block" }}
          tabIndex="-1"
          role="dialog"
        >
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header bg-danger text-white">
                <h5 className="modal-title">Confirm Deletion</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body">
                <p>Are you sure you want to delete customer with ID {selectedCustomerId}?</p>
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => setShowModal(false)}
                >
                  Cancel
                </button>
                <button
                  type="button"
                  className="btn btn-danger"
                  onClick={handleDeleteConfirm}
                >
                  Confirm
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ExistingCustomers;
