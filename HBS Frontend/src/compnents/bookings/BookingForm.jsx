import React, { useState, useEffect } from "react";
import moment from "moment";
import { getRoomById } from "../utils/ApiFunctions";
import { useParams } from "react-router-dom";
import { Form, FormControl } from "react-bootstrap";
import BookingSummary from "./BookingSummary";

// Declare the generateUUID function outside the component
const generateUUID = () => {
  return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, (c) =>
    (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16)
  );
};

const BookingForm = () => {
  const [validated, setValidated] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [roomPrice, setRoomPrice] = useState(0);
  const [booking, setBooking] = useState({
    bookingId: 1,
    username: "",
    roomId: 0,
    checkinDate: "",
    checkoutDate: "",
    totalPerson: 0,
    confirmationCode: generateUUID(), // Generate dynamic UUID
    bookingStatus: "BOOKED",
  });

  const { id } = useParams();

  useEffect(() => {
    const fetchRoomPrice = async () => {
      try {
        const response = await getRoomById(id);
        if (response) {
          setRoomPrice(response.price);
          setBooking((prevBooking) => ({
            ...prevBooking,
            roomId: parseInt(id, 10),
          }));
        }
      } catch (error) {
        console.error("Error fetching room price:", error);
        setErrorMessage("Failed to fetch room data.");
      }
    };
    fetchRoomPrice();
  }, [id]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setBooking((prevBooking) => ({
      ...prevBooking,
      [name]: name === "totalPerson" ? parseInt(value, 10) : value,
    }));
    setErrorMessage("");
  };

  const calculatePayment = () => {
    const checkinDate = moment(booking.checkinDate);
    const checkoutDate = moment(booking.checkoutDate);
    const diffInDays = checkoutDate.diff(checkinDate, "days");
    return diffInDays > 0 ? diffInDays * roomPrice : 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const form = e.currentTarget;
    if (form.checkValidity() === false || !isCheckOutDateValid() || !isGuestCountValid()) {
      e.stopPropagation();
    } else {
      setIsSubmitted(true);
    }
    setValidated(true);
  };

  const isCheckOutDateValid = () => {
    const isValid = moment(booking.checkoutDate).isSameOrAfter(moment(booking.checkinDate));
    if (!isValid) setErrorMessage("Check-out date must be after check-in date.");
    return isValid;
  };

  const isGuestCountValid = () => {
    if (booking.totalPerson < 1) {
      setErrorMessage("Total guests must be at least 1.");
      return false;
    }
    return true;
  };

  return (
    <div className="container mb-5">
      <div className="row">
        {/* Booking Form */}
        <div className="col-md-6">
          <div className="card card-body mt-5">
            <h4 className="card-title">Reserve Room</h4>
            <Form noValidate validated={validated} onSubmit={handleSubmit}>
              {/* Username Field */}
              <Form.Group>
                <Form.Label htmlFor="username" className="hotel-color">
                  Username
                </Form.Label>
                <FormControl
                  required
                  type="text"
                  id="username"
                  name="username"
                  value={booking.username}
                  placeholder="Enter your username"
                  onChange={handleInputChange}
                />
                <Form.Control.Feedback type="invalid">
                  Please enter your username.
                </Form.Control.Feedback>
              </Form.Group>

              {/* Lodging Period Fields */}
              <fieldset style={{ border: "2px" }}>
                <legend>Lodging Period</legend>
                <div className="row">
                  <div className="col-6">
                    <Form.Label htmlFor="checkinDate" className="hotel-color">
                      Check-in date
                    </Form.Label>
                    <FormControl
                      required
                      type="date"
                      id="checkinDate"
                      name="checkinDate"
                      value={booking.checkinDate}
                      min={moment().format("YYYY-MM-DD")}
                      onChange={handleInputChange}
                    />
                    <Form.Control.Feedback type="invalid">
                      Please select a check-in date.
                    </Form.Control.Feedback>
                  </div>
                  <div className="col-6">
                    <Form.Label htmlFor="checkoutDate" className="hotel-color">
                      Check-out date
                    </Form.Label>
                    <FormControl
                      required
                      type="date"
                      id="checkoutDate"
                      name="checkoutDate"
                      value={booking.checkoutDate}
                      min={moment().format("YYYY-MM-DD")}
                      onChange={handleInputChange}
                    />
                    <Form.Control.Feedback type="invalid">
                      Please select a check-out date.
                    </Form.Control.Feedback>
                  </div>
                  {errorMessage && (
                    <p className="error-message text-danger">{errorMessage}</p>
                  )}
                </div>
              </fieldset>

              {/* Guest Count Field */}
              <fieldset style={{ border: "2px" }}>
                <legend>Total Guests</legend>
                <div className="row"> 
                  <div className="col-12">
                    <Form.Label htmlFor="totalPerson" className="hotel-color">
                      Total Guests
                    </Form.Label>
                    <FormControl
                      required
                      type="number"
                      id="totalPerson"
                      name="totalPerson"
                      value={booking.totalPerson || ""}
                      min={1}
                      onChange={handleInputChange}
                    />
                    <Form.Control.Feedback type="invalid">
                      Please enter the total number of guests (at least 1).
                    </Form.Control.Feedback>
                  </div>
                </div>
              </fieldset>

              {/* Submit Button */}
              <div className="form-group mt-2 mb-2">
                <button type="submit" className="btn btn-hotel">
                  Continue
                </button>
              </div>
            </Form>
          </div>
        </div>

        {/* Booking Summary */}
        <div className="col-md-4">
          {isSubmitted && (
            <BookingSummary
              booking={{
                ...booking,
                payment: calculatePayment(),
              }}
              payment={calculatePayment()}
              isFormValid={validated}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default BookingForm;
