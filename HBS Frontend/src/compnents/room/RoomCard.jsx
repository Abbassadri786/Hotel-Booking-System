import React from 'react';
import { Card, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const RoomCard = ({ room }) => {
  return (
    <Col key={room.roomId} className="mb-4" xs={12}>
      <Card>
        <Card.Body className="d-flex flex-wrap align-items-center">
          {/* Room Image */}
          <div className="flex-shrink-0 mr-3 mb-3 mb-md-0">
            <Link to={`/book-room/${room.roomId}`} className="btn btn-hotel btn-sm">
              <Card.Img
                variant="top"
                src={`${room.photo}`}
                alt="Room Photo"
                style={{ width: '100%', maxWidth: '200px', height: 'auto' }}
              />
            </Link>
          </div>

          {/* Room Details */}
          <div className="flex-grow-1 ml-3 px-5">
            <Card.Title className="hotel-color">
              {room.roomType} - Room {room.roomNum}
            </Card.Title>

            <Card.Title className="room-price">
              ₹{room.price.toFixed(2)}
            </Card.Title>

            <Card.Text>
              Capacity: {room.totalPerson} {room.totalPerson > 1 ? 'people' : 'person'}
            </Card.Text>

            <Card.Text>
              {room.available ? (
                <span className="badge bg-success">Available</span>
              ) : (
                <span className="badge bg-danger">Not Available</span>
              )}
            </Card.Text>
          </div>

          {/* Book Now Button */}
          <div className="flex-shrink-0 mt-3">
            <Link to={`/book-room/${room.roomId}`} className="btn btn-hotel btn-sm">
              Book Now
            </Link>
          </div>
        </Card.Body>
      </Card>
    </Col>
  );
};

export default RoomCard;
