import React from 'react'
import { Container, Row, Col, Card } from 'react-bootstrap'
import { FaClock, FaCocktail, FaParking, FaTshirt, FaUtensils, FaWifi, FaSnowflake, FaSpa, FaSwimmer, FaDumbbell } from 'react-icons/fa'

const HotelService = () => {
  return (
    <>
      <Container className='mb-2'>
        <h1 title={"Our Services"} />
        <Row>
          <h4 className='text-center'>
            Services at <span className='hotel-color'>The Amber Lotus </span>Resort{' '}
            <span className='gap-2'>
              <FaClock /> - 24 Hour Front Desk
            </span>
          </h4>
        </Row>
        <hr />
        <Row xs={1} md={2} lg={3} className='g-4 mt-2'>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className='hotel-color'>
                  <FaWifi /> WiFi
                </Card.Title>
                <Card.Text>
                  Stay Connected with high-speed internet access
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <Card.Title className='hotel-color'>
                  <FaUtensils /> Breakfast
                </Card.Title>
                <Card.Text>
                  Start your day right with our fresh and delightful breakfast
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <Card.Title className='hotel-color'>
                  <FaCocktail /> Mini-Bar
                </Card.Title>
                <Card.Text>
                  Unwind and savor the finest drinks at our vibrant bar
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <Card.Title className='hotel-color'>
                  <FaTshirt /> Laundry
                </Card.Title>
                <Card.Text>
                  Keep your clothes clean and fresh with our service
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <Card.Title className='hotel-color'>
                  <FaParking /> Parking
                </Card.Title>
                <Card.Text>
                  Experience hassle-free convenience with our valet parking
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <Card.Title className='hotel-color'>
                  <FaSnowflake /> Air Conditioning
                </Card.Title>
                <Card.Text>
                  Relax in cool, climate-controlled comfort with our environment
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <Card.Title className='hotel-color'>
                  <FaSpa /> Spa
                </Card.Title>
                <Card.Text>
                  Indulge in rejuvenating therapies and treatments at our luxury spa
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <Card.Title className='hotel-color'>
                  <FaSwimmer /> Swimming Pool
                </Card.Title>
                <Card.Text>
                  Dive into relaxation with our pristine and inviting pool
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <Card.Title className='hotel-color'>
                  <FaDumbbell /> Fitness Center
                </Card.Title>
                <Card.Text>
                  Stay active and energized with our state-of-the-art gym facilities
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </>
  )
}

export default HotelService
