import React, { useState } from "react";
import { Form, Button, Row, Col, Container } from "react-bootstrap";
import moment from "moment";
import { getAvailableRooms } from "../utils/ApiFunctions";
import RoomSearchResults from "./RoomSearchResult";
import RoomTypeSelector from "./RoomTypeSelector";

const RoomSearch = () => {
  const [searchQuery, setSearchQuery] = useState({
    checkInDate: "",
    checkOutDate: "",
    roomType: "",
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [availableRooms, setAvailableRooms] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const handleSearch = async (e) => {
    e.preventDefault();

    const checkInMoment = moment(searchQuery.checkInDate);
    const checkOutMoment = moment(searchQuery.checkOutDate);

    if (!checkInMoment.isValid() || !checkOutMoment.isValid()) {
      setErrorMessage("Please enter valid dates.");
      return;
    }
    if (!checkOutMoment.isSameOrAfter(checkInMoment)) {
      setErrorMessage("Check-out date must be after check-in date.");
      return;
    }

    if (!searchQuery.roomType) {
      setErrorMessage("Please select a room type.");
      return;
    }

    setErrorMessage("");
    setIsLoading(true);

    try {
      const response = await getAvailableRooms(searchQuery.roomType);

      if (Array.isArray(response)) {
        setAvailableRooms(response);
      } else {
        setErrorMessage("Unexpected response from the server.");
      }
    } catch (error) {
      console.error(error);
      setErrorMessage(error.message || "Error fetching rooms.");
    } finally {
      setIsLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSearchQuery({ ...searchQuery, [name]: value });
  };

  const handleClearSearch = () => {
    setSearchQuery({
      checkInDate: "",
      checkOutDate: "",
      roomType: "",
    });
    setAvailableRooms([]);
    setErrorMessage("");
  };

  return (
    <>
      <Container className="shadow mt-n5 mb-5 py-5">
        <Form onSubmit={handleSearch}>
          <Row className="justify-content-center">
            <Col xs={12} md={3}>
              <Form.Group controlId="checkInDate">
                <Form.Label>Check-in Date</Form.Label>
                <Form.Control
                  type="date"
                  name="checkInDate"
                  value={searchQuery.checkInDate}
                  onChange={handleInputChange}
                  min={moment().format("YYYY-MM-DD")}
                />
              </Form.Group>
            </Col>

            <Col xs={12} md={3}>
              <Form.Group controlId="checkOutDate">
                <Form.Label>Check-out Date</Form.Label>
                <Form.Control
                  type="date"
                  name="checkOutDate"
                  value={searchQuery.checkOutDate}
                  onChange={handleInputChange}
                  min={moment().format("YYYY-MM-DD")}
                />
              </Form.Group>
            </Col>

            <Col xs={12} md={3}>
              <Form.Group controlId="roomType">
                <Form.Label>Room Type</Form.Label>
                <div className="d-flex">
                  <RoomTypeSelector
                    handleRoomInputChange={handleInputChange}
                    newRoom={searchQuery}
                  />
                  <Button variant="secondary" type="submit" className="ml-2">
                    Search
                  </Button>
                </div>
              </Form.Group>
            </Col>
          </Row>
        </Form>

        {isLoading && <p className="mt-4">Finding available rooms...</p>}

        {!isLoading && availableRooms.length > 0 && (
          <RoomSearchResults
            results={availableRooms}
            onClearSearch={handleClearSearch}
          />
        )}

        {!isLoading && availableRooms.length === 0 && !errorMessage && (
          <p className="mt-4">
            No rooms available for the selected room type.
          </p>
        )}

        {errorMessage && <p className="text-danger">{errorMessage}</p>}
      </Container>
    </>
  );
};

export default RoomSearch;
