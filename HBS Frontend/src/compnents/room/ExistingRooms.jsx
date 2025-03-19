import React, { useEffect, useState } from "react";
import { deleteRoom, getAllRooms } from "../utils/ApiFunctions";
import RoomPaginator from "../common/RoomPaginator";
import RoomFilter from "../common/RoomFilter";
import "bootstrap/dist/css/bootstrap.min.css";
import { Col, Row } from "react-bootstrap";
import { FaTrashAlt, FaEye, FaEdit, FaPlus } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom"; // Added useNavigate for navigation

const ExistingRooms = () => {
  const [rooms, setRooms] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [roomsPerPage, setRoomsPerPage] = useState(8);
  const [isLoading, setIsLoading] = useState(false);
  const [filteredRooms, setFilteredRooms] = useState([]);
  const [selectedRoomType, setSelectedRoomType] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [showModal, setShowModal] = useState(false); // Modal visibility state
  const [selectedRoomId, setSelectedRoomId] = useState(null); // Selected room ID for deletion
  const navigate = useNavigate(); // Added navigate function for Back button

  useEffect(() => {
    fetchRooms(); // Initial fetch for all rooms
  }, []);

  const fetchRooms = async () => {
    setIsLoading(true);
    try {
      const result = await getAllRooms();
      if (Array.isArray(result)) {
        setRooms(result);
        setFilteredRooms(result);
      } else {
        throw new Error("API did not return an array of rooms");
      }
    } catch (error) {
      setErrorMessage(`Error fetching rooms: ${error.message}`);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    if (selectedRoomType === "") {
      setFilteredRooms(rooms); // Show all rooms if no type is selected
    } else {
      const filtered = rooms.filter((room) => room.roomType === selectedRoomType);
      setFilteredRooms(filtered);
    }
  }, [rooms, selectedRoomType]);

  const handlePaginationClick = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const openModal = (id) => {
    setSelectedRoomId(id); // Set the room ID to delete
    setShowModal(true); // Show the modal
  };

  const handleDeleteConfirm = async () => {
    try {
      await deleteRoom(selectedRoomId); // Delete the selected room
      setSuccessMessage(`Room with ID ${selectedRoomId} was deleted successfully.`);

      // Fetch updated list of rooms after deletion
      fetchRooms();

      // Close the modal
      setShowModal(false);
    } catch (error) {
      setErrorMessage(`Error deleting room: ${error.message}`);
    } finally {
      // Reset success and error messages after 3 seconds
      setTimeout(() => {
        setSuccessMessage("");
        setErrorMessage("");
      }, 3000);
    }
  };

  const calculateTotalPages = (filteredRooms, roomsPerPage) => {
    const totalRooms = filteredRooms.length > 0 ? filteredRooms.length : rooms.length;
    return Math.ceil(totalRooms / roomsPerPage);
  };

  const indexOfLastRoom = currentPage * roomsPerPage;
  const indexOfFirstRoom = indexOfLastRoom - roomsPerPage;
  const currentRooms = filteredRooms.slice(indexOfFirstRoom, indexOfLastRoom);

  return (
    <>
      <div className="container col-md-8 col-lg-6">
        {successMessage && <p className="alert alert-success mt-5">{successMessage}</p>}
        {errorMessage && <p className="alert alert-danger mt-5">{errorMessage}</p>}
      </div>
      {isLoading ? (
        <p>Loading existing rooms...</p>
      ) : errorMessage ? (
        <p>Error: {errorMessage}</p>
      ) : (
        <>
          <section className="mt-5 mb-5 container">
            <div className="d-flex justify-content-between mb-3 mt-5">
              <h2>Existing Rooms</h2>
            </div>

            <Row>
              <Col md={6} className="mb-3 mb-md-0">
                <RoomFilter data={rooms} setFilteredData={setFilteredRooms} />
              </Col>
              <Col md={6} className="d-flex justify-content-end">
                <Link to="/add-room">
                  <FaPlus /> Add Room
                </Link>
              </Col>
            </Row>

            <table className="table table-bordered table-hover">
              <thead>
                <tr className="text-center">
                  <th>ID</th>
                  <th>Room Number</th>
                  <th>Room Type</th>
                  <th>Capacity</th>
                  <th>Price</th>
                  <th>Photo</th>
                  <th>Availability</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {currentRooms.map((room) => (
                  <tr key={room.roomId} className="text-center">
                    <td>{room.roomId}</td>
                    <td>{room.roomNum}</td>
                    <td>{room.roomType}</td>
                    <td>{room.totalPerson}</td>
                    <td>{room.price}</td>
                    <td>
                      {room.photo ? (
                        <img
                          src={`${room.photo}`}
                          alt="Room"
                          style={{ width: "100px", height: "auto" }}
                        />
                      ) : (
                        <p>No Photo Available</p>
                      )}
                    </td>
                    <td>
                      <span
                        className={`badge ${
                          room.available ? "bg-success" : "bg-danger"
                        }`}
                      >
                        {room.available ? "Available" : "Not Available"}
                      </span>
                    </td>
                    <td className="gap-2">
                      <Link to={`/edit-room/${room.roomId}`}>
                        <span className="btn btn-info btn-sm">
                          <FaEye />
                        </span>
                        <span className="btn btn-warning btn-sm">
                          <FaEdit />
                        </span>
                      </Link>
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => openModal(room.roomId)}
                      >
                        <FaTrashAlt />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <RoomPaginator
              currentPage={currentPage}
              totalPages={calculateTotalPages(filteredRooms, roomsPerPage)}
              onPageChange={handlePaginationClick}
            />
          </section>

          {/* Back Button */}
          <div className="d-flex justify-content-end mt-4">
            <button className="btn btn-secondary" onClick={() => navigate("/admin")}>
              Back to Admin
            </button>
          </div>
        </>
      )}

      {/* Bootstrap Modal for Confirmation */}
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
                <p>Are you sure you want to delete room with ID {selectedRoomId}?</p>
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

export default ExistingRooms;
