import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Carousel } from 'react-bootstrap';
import { getAllFeedbacks } from '../utils/ApiFunctions';

const FeedbackCarousel = () => {
  const [feedbacks, setFeedbacks] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    getAllFeedbacks()
      .then((data) => {
        console.log("Feedback data received:", data);
        if (Array.isArray(data) && data.length > 0) {
          setFeedbacks(data);
        } else {
          console.warn("Feedback data is empty or invalid:", data);
          setFeedbacks([]);
        }
        setIsLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching feedbacks:", error.message);
        setErrorMessage(error.message);
        setIsLoading(false);
      });
  }, []);

  if (isLoading) {
    return <div className="mt-5">Loading feedback...</div>;
  }

  if (errorMessage) {
    return <div className="text-danger mb-5 mt-5">Error: {errorMessage}</div>;
  }

  if (!feedbacks || feedbacks.length === 0) {
    return <div className="mt-5">No feedbacks available to display.</div>;
  }

  return (
    <section className="bg-light mb-5 mt-5 shadow">
      <Container>
        <h5 className="hotel color text-center mb-3 d-block h5">User Feedback</h5>
        <Carousel indicators={false}>
          {[...Array(Math.ceil(feedbacks.length / 4))].map((_, index) => (
            <Carousel.Item key={index}>
              <Row>
                {feedbacks.slice(index * 4, index * 4 + 4).map((feedback, idx) => (
                  <Col key={idx} className="mb-4" xs={12} md={6} lg={3}>
                    <Card>
                      <Card.Body>
                        <Card.Text>
                          <strong>{feedback.name}</strong> {/* Displaying the name */}
                        </Card.Text>
                        <Card.Text style={{ color: "darkgoldenrod" }}>
                          {feedback.review}
                        </Card.Text>
                        <Card.Footer>
                          <small>
                            <span>Overall Rating:</span> {feedback.rating}
                          </small>
                        </Card.Footer>
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

export default FeedbackCarousel;
