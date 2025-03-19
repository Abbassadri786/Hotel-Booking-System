import React, { useState, useEffect } from "react";
import moment from "moment";
import { useNavigate } from "react-router-dom";
import { Button } from "react-bootstrap";
import { bookRoom } from "../utils/ApiFunctions";

const BookingSummary = ({ booking = {}, payment, isFormValid, onConfirm }) => {
  const checkinDate = moment(booking.checkinDate);
  const checkoutDate = moment(booking.checkoutDate);
  const numberOfDays = checkoutDate.diff(checkinDate, "days");

  const [isBookingConfirmed, setIsBookingConfirmed] = useState(false);
  const [isProcessingPayment, setIsProcessingPayment] = useState(false);
  const navigate = useNavigate();

  const handleConfirmBooking = async () => {
    setIsProcessingPayment(true);
    try {
      console.log("Booking Object:", booking);

      const bookingResponse = await bookRoom(booking);
      if (bookingResponse) {
        setIsBookingConfirmed(true);
        onConfirm();
      }
    } catch (error) {
      console.error("Error confirming booking:", error);
      setIsProcessingPayment(false);
    }
  };

  useEffect(() => {
    if (isBookingConfirmed) {
      console.log("Navigating to /booking-success");
      navigate("/booking-success");
    }
  }, [isBookingConfirmed, navigate]);

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
        <>
          {isFormValid && !isBookingConfirmed ? (
            <Button variant="success" onClick={handleConfirmBooking}>
              {isProcessingPayment ? (
                <>
                  <span
                    className="spinner-border spinner-border-sm mr-2"
                    role="status"
                    aria-hidden="true"
                  ></span>
                  Booking Confirmed, redirecting to payment...
                </>
              ) : (
                "Confirm Booking & Proceed to Payment"
              )}
            </Button>
          ) : isBookingConfirmed ? (
            <div className="d-flex justify-content-center align-items-center">
              <div className="spinner-border text-primary" role="status">
                <span className="sr-only">Loading...</span>
              </div>
            </div>
          ) : null}
        </>
      ) : (
        <p className="text-danger">Check-out date must be after check-in date.</p>
      )}
    </div>
  );
};

export default BookingSummary;
