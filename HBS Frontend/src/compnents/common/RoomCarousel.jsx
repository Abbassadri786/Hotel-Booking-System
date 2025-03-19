import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Container, Row, Col, Card, Carousel } from 'react-bootstrap';
import { getAllRooms } from '../utils/ApiFunctions';

const RoomCarousel = () => {
    const [rooms, setRooms] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true);
        getAllRooms()
            .then((data) => {
                setRooms(data);
                setIsLoading(false);
            })
            .catch((error) => {
                setErrorMessage(error.message);
                setIsLoading(false);
            });
    }, []);

    if (isLoading) {
        return <div className='mt-5'>Loading rooms...</div>;
    }

    if (errorMessage) {
        return <div className='text-danger mb-5 mt-5'>Error: {errorMessage}</div>;
    }

    return (
        <section className='bg-light mb-5 mt-5 shadow'>
            <Link to={`/browse-all-rooms`} className="hotel-color text-center mb-3 d-block">
                Browse all rooms
            </Link>
            <Container>
                <Carousel indicators={false}>
                    {/* Display rooms in groups of 4 */}
                    {[...Array(Math.ceil(rooms.length / 4))].map((_, index) => (
                        <Carousel.Item key={index}>
                            <Row>
                                {rooms.slice(index * 4, index * 4 + 4).map((room) => (
                                    <Col key={room.roomId} className="mb-4" xs={12} md={6} lg={3}>
                                        <Card>
                                            <Link to={`/book-room/${room.roomId}`}>
                                                <Card.Img
                                                    variant="top"
                                                    src={`${room.photo}`}
                                                    alt="Room Photo"
                                                    className="w-100"
                                                    style={{ height: "200px", objectFit: "cover" }}
                                                />
                                            </Link>
                                            <Card.Body>
                                                <div className="ml-3 px-3">
                                                    <Card.Title className="hotel-color">
                                                        {room.roomType} - Room {room.roomNum}
                                                    </Card.Title>
                                                    <Card.Title className="room-price">
                                                        ₹{room.price.toFixed(2)}/night
                                                    </Card.Title>
                                                    <Card.Text>
                                                        Capacity: {room.totalPerson} {room.totalPerson > 1 ? 'people' : 'person'}
                                                    </Card.Text>
                                                </div>
                                                <div className="mt-3 text-center">
                                                    <Link to={`/book-room/${room.roomId}`} className="btn btn-hotel btn-sm">
                                                        Book Now
                                                    </Link>
                                                </div>
                                            </Card.Body>
                                        </Card>
                                    </Col>
                                ))}
                            </Row>
                        </Carousel.Item>
                    ))}
                </Carousel>
            </Container>
        </section>
    );
};

export default RoomCarousel;
