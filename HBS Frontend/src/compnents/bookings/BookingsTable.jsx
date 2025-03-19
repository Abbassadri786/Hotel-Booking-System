import React, { useState, useEffect } from "react";
import { parseISO } from "date-fns";
import DateSlider from "../common/DateSlider";
import { cancelBooking } from "../utils/ApiFunctions"; // Import the cancelBooking API function
import { useNavigate } from "react-router-dom"; // Import useNavigate for navigation

const BookingsTable = ({ bookingInfo }) => {
  const [filteredBookings, setFilteredBookings] = useState([]);
  const navigate = useNavigate(); // Initialize navigate function

  const filterBookings = (startDate, endDate) => {
    let filtered = bookingInfo;
    if (startDate && endDate) {
      filtered = bookingInfo.filter((booking) => {
        const bookingStartDate = parseISO(booking.checkinDate);
        const bookingEndDate = parseISO(booking.checkoutDate);
        return (
          bookingStartDate >= startDate &&
          bookingEndDate <= endDate &&
          bookingEndDate > startDate
        );
      });
    }
    setFilteredBookings(filtered);
  };

  const handleBookingCancellation = async (bookingId) => {
    if (window.confirm("Are you sure you want to cancel this booking?")) {
      try {
        // Call the cancelBooking API
        await cancelBooking(bookingId);
        alert("Booking canceled successfully!");

        // Re-render filtered bookings excluding the canceled one
        const updatedBookings = filteredBookings.filter(
          (booking) => booking.bookingId !== bookingId
        );
        setFilteredBookings(updatedBookings);
      } catch (error) {
        console.error("Error canceling booking:", error.message);
        alert("Failed to cancel the booking. Please try again.");
      }
    }
  };

  useEffect(() => {
    console.log("Booking Info:", bookingInfo);
    setFilteredBookings(bookingInfo || []);
  }, [bookingInfo]);

  return (
    <section className="p-4">
      <DateSlider onDateChange={filterBookings} onFilterChange={filterBookings} />
      <table className="table table-bordered table-hover shadow">
        <thead>
          <tr>
            <th>Booking ID</th>
            <th>Room ID</th>
            <th>Check-In Date</th>
            <th>Check-Out Date</th>
            <th>Guest Username</th>
            <th>Total Guests</th>
            <th>Payment</th>
            <th>Booking Status</th>
            <th>Confirmation Code</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody className="text-center">
          {filteredBookings.length > 0 ? (
            filteredBookings.map((booking) => (
              <tr key={booking?.bookingId || booking?.id}>
                <td>{booking?.bookingId || "N/A"}</td>
                <td>{booking?.roomId || "N/A"}</td>
                <td>{booking?.checkinDate || "N/A"}</td>
                <td>{booking?.checkoutDate || "N/A"}</td>
                <td>{booking?.username || "N/A"}</td>
                <td>{booking?.totalPerson || "N/A"}</td>
                <td>{booking?.payment ? `₹${booking.payment}` : "N/A"}</td>
                <td>{booking?.booking_status || "N/A"}</td>
                <td>{booking?.Confirmation_code || "N/A"}</td>
                <td>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() =>
                      booking?.bookingId && handleBookingCancellation(booking.bookingId)
                    }
                  >
                    Cancel
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="10">No bookings found for the selected dates</td>
            </tr>
          )}
        </tbody>
      </table>

      {/* Back Button */}
      <div className="d-flex justify-content-end mt-4">
        <button
          className="btn btn-secondary"
          onClick={() => navigate("/admin")} // Navigate back to the admin page
        >
          Back to Admin
        </button>
      </div>
    </section>
  );
};

export default BookingsTable;
