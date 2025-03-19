import React, { useState } from "react";
import moment from "moment";
import { getBookingByUsername, cancelBooking } from "../utils/ApiFunctions";

const FindBooking = () => {
  const [username, setUsername] = useState("");
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [bookings, setBookings] = useState([]);

  const handleInputChange = (event) => {
    setUsername(event.target.value);
  };

  const handleFormSubmit = async (event) => {
    event.preventDefault();
    setIsLoading(true);

    try {
      const data = await getBookingByUsername(username);
      setBookings(data); 
      setError(null);
    } catch (error) {
      setBookings([]);
      if (error.response && error.response.status === 404) {
        setError(error.response.data.message);
      } else {
        setError(error.message);
      }
    }

    setTimeout(() => setIsLoading(false), 2000);
  };

  const handleBookingCancellation = async (bookingId) => {
    try {
      await cancelBooking(bookingId);
      setSuccessMessage("Booking has been cancelled successfully!");
      setBookings((prevBookings) =>
        prevBookings.filter((booking) => booking.bookingId !== bookingId)
      );
      setError(null);
    } catch (error) {
      setError(error.message);
    }
    setTimeout(() => setSuccessMessage(""), 2000);
  };

  return (
    <>
      <div className="container mt-5 d-flex flex-column justify-content-center align-items-center">
        <h2 className="text-center mb-4">Find My Booking</h2>
        <form onSubmit={handleFormSubmit} className="col-md-6">
          <div className="input-group mb-3">
            <input
              className="form-control"
              type="text"
              id="username"
              name="username"
              value={username}
              onChange={handleInputChange}
              placeholder="Enter your username"
              required
            />
            <button type="submit" className="btn btn-hotel input-group-text">
              Find Booking
            </button>
          </div>
        </form>

        {isLoading ? (
          <div>Finding your booking...</div>
        ) : error ? (
          <div className="text-danger">Error: {error}</div>
        ) : bookings.length > 0 ? (
          <div className="col-md-6 mt-4 mb-5">
            <h3>Booking Information</h3>
            {bookings.map((booking) => (
              <div key={booking.bookingId} className="booking-details mb-4">
                <p className="text-success">
                  Confirmation Code: {booking.confirmationCode}
                </p>
                <p>Room Number: {booking.roomNum || "N/A"}</p>
                <p>Room Type: {booking.roomType || "N/A"}</p>
                <p>
                  Check-in Date:{" "}
                  {moment(booking.checkinDate).format("MMM Do, YYYY")}
                </p>
                <p>
                  Check-out Date:{" "}
                  {moment(booking.checkoutDate).format("MMM Do, YYYY")}
                </p>
                <p>Total Guests: {booking.totalPerson}</p>
                <p>Payment: ₹ {booking.payment.toFixed(2) || "N/A"}</p>
                <p>Booking Status: {booking.bookingStatus || "N/A"}</p>
                <button
                  onClick={() => handleBookingCancellation(booking.bookingId)}
                  className="btn btn-danger"
                >
                  Cancel Booking
                </button>
              </div>
            ))}
          </div>
        ) : (
          <div>No bookings found.</div>
        )}

        {successMessage && (
          <div className="alert alert-success mt-3 fade show">
            {successMessage}
          </div>
        )}
      </div>
    </>
  );
};

export default FindBooking;
