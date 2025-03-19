import React, { useState, useEffect } from "react";
import moment from "moment";
import { bookRoom, getRoomById } from "../utils/ApiFunctions";
import { useParams, useNavigate } from "react-router-dom";
import { Form, FormControl } from "react-bootstrap";
import BookingSummary from "./BookingSummary";

const BookingForm = () => {
  const [validated, setValidated] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [roomPrice, setRoomPrice] = useState(0);
  const [booking, setBooking] = useState({
    username: "",
    roomId: "",
    checkInDate: "",
    checkOutDate: "",
    totalPerson: "",
  });

  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRoomPrice = async () => {
      try {
        const response = await getRoomById(id);
        if (response) {
          setRoomPrice(response.price);
          setBooking((prevBooking) => ({
            ...prevBooking,
            roomId: id, // Set roomId based on the URL parameter
          }));
        }
      } catch (error) {
        console.error("Error fetching room price:", error);
      }
    };
    fetchRoomPrice();
  }, [id]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setBooking((prevBooking) => ({ ...prevBooking, [name]: value }));
    setErrorMessage("");
  };

  const calculatePayment = () => {
    const checkInDate = moment(booking.checkInDate);
    const checkOutDate = moment(booking.checkOutDate);
    const diffInDays = checkOutDate.diff(checkInDate, "days");
    const paymentPerDay = roomPrice || 0;
    return diffInDays > 0 ? diffInDays * paymentPerDay * parseInt(booking.totalPerson || 0) : 0;
  };

  const isCheckOutDateValid = () => {
    if (!moment(booking.checkOutDate).isSameOrAfter(moment(booking.checkInDate))) {
      setErrorMessage("Check-out date must be after check-in date.");
      return false;
    }
    return true;
  };

  const isGuestCountValid = () => {
    const totalGuests = parseInt(booking.totalPerson || 0);
    if (totalGuests < 1) {
      setErrorMessage("Total guests must be at least 1.");
      return false;
    }
    return true;
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

  const handleBooking = async () => {
    try {
      const res = await bookRoom(booking);
      bookingConfirmationCode = res.ConfirmationCode
      setIsSubmitted(true);
      navigate(`/booking-success`, { state: { message: bookingConfirmationCode } });
    } catch (error) {
      console.error("Error booking room:", error);
      setErrorMessage(error.message);
      navigate("/", { state: { error: error.message } });
    }
  };

  return (
    <div className="container mb-5">
      <div className="row">
        <div className="col-md-6">
          <div className="card card-body mt-5">
            <h4 className="card-title">Reserve Room</h4>
            <Form noValidate validated={validated} onSubmit={handleSubmit}>
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

              <fieldset style={{ border: "2px" }}>
                <legend>Lodging Period</legend>
                <div className="row">
                  <div className="col-6">
                    <Form.Label htmlFor="checkInDate" className="hotel-color">
                      Check-in date
                    </Form.Label>
                    <FormControl
                      required
                      type="date"
                      id="checkInDate"
                      name="checkInDate"
                      value={booking.checkInDate}
                      min={moment().format("YYYY-MM-DD")}
                      onChange={handleInputChange}
                    />
                    <Form.Control.Feedback type="invalid">
                      Please select a check-in date.
                    </Form.Control.Feedback>
                  </div>
                  <div className="col-6">
                    <Form.Label htmlFor="checkOutDate" className="hotel-color">
                      Check-out date
                    </Form.Label>
                    <FormControl
                      required
                      type="date"
                      id="checkOutDate"
                      name="checkOutDate"
                      value={booking.checkOutDate}
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
                      value={booking.totalPerson}
                      min={1}
                      onChange={handleInputChange}
                    />
                    <Form.Control.Feedback type="invalid">
                      Please enter the total number of guests (at least 1).
                    </Form.Control.Feedback>
                  </div>
                </div>
              </fieldset>

              <div className="form-group mt-2 mb-2">
                <button type="submit" className="btn btn-hotel">
                  Continue
                </button>
              </div>
            </Form>
          </div>
        </div>

        <div className="col-md-4">
          {isSubmitted && (
            <BookingSummary
              booking={{ ...booking, id }}
              payment={calculatePayment()}
              onConfirm={handleBooking}
              isFormValid={validated}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default BookingForm;
