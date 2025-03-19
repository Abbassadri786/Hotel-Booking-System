import React, { useState } from "react";
import moment from "moment";
import { Button, Modal } from "react-bootstrap";
import { bookRoom } from "../utils/ApiFunctions";

const BookingSummary = ({ booking = {}, payment, isFormValid }) => {
  const checkinDate = moment(booking.checkinDate);
  const checkoutDate = moment(booking.checkoutDate);
  const numberOfDays = checkoutDate.diff(checkinDate, "days");

  const [showModal, setShowModal] = useState(false);
  const [confirmationCode, setConfirmationCode] = useState("");
  const [isProcessingBooking, setIsProcessingBooking] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleBookNow = async () => {
    setIsProcessingBooking(true);
    setErrorMessage("");
    try {
      const bookingData = {
        bookingId: booking.bookingId,
        username: booking.username,
        roomId: parseInt(booking.roomId, 10),
        checkinDate: booking.checkinDate,
        checkoutDate: booking.checkoutDate,
        totalPerson: parseInt(booking.totalPerson, 10),
        payment: payment,
        confirmationCode: booking.confirmationCode,
        bookingStatus: "BOOKED",
      };
  
      const bookingResponse = await bookRoom(bookingData);
      if (bookingResponse && bookingResponse.ConfirmationCode) {
        setConfirmationCode(bookingResponse.ConfirmationCode);
        setShowModal(true);
      }
    } catch (error) {
      console.error("Error while booking:", error);
      setErrorMessage("Failed to book the room. Please try again.");
    } finally {
      setIsProcessingBooking(false);
    }
  };
  

  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <div className="card card-body mt-5">
      <h4 className="card-title hotel-color">Reservation Summary</h4>
      <p>
        Username: <strong>{booking.username || "N/A"}</strong>
      </p>
      <p>
        Room ID: <strong>{booking.roomId || "N/A"}</strong>
      </p>
      <p>
        Check-in Date:{" "}
        <strong>
          {checkinDate.isValid() ? checkinDate.format("MMM Do YYYY") : "N/A"}
        </strong>
      </p>
      <p>
        Check-out Date:{" "}
        <strong>
          {checkoutDate.isValid() ? checkoutDate.format("MMM Do YYYY") : "N/A"}
        </strong>
      </p>
      <p>
        Total Number of Guests: <strong>{booking.totalPerson || 0}</strong>
      </p>
      <p>
        Total Payment: <strong>₹{payment.toFixed(2) || "N/A"}</strong>
      </p>

      {payment > 0 && numberOfDays > 0 ? (
        isFormValid ? (
          <Button
            variant="success"
            onClick={handleBookNow}
            disabled={isProcessingBooking}
          >
            {isProcessingBooking ? "Booking..." : "Book Now"}
          </Button>
        ) : null
      ) : (
        <p className="text-danger">Please ensure all booking details are valid.</p>
      )}

      {errorMessage && <p className="text-danger">{errorMessage}</p>}

      {/* Bootstrap Modal */}
      <Modal show={showModal} onHide={handleCloseModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>Booking Confirmed</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>Your booking has been successfully completed!</p>
          <p>
            <strong>Confirmation Code:</strong> {confirmationCode}
          </p>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="success" onClick={handleCloseModal}>
            Okay
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default BookingSummary;
