import React, { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { submitFeedback } from '../utils/ApiFunctions';

const FeedbackForm = () => {
  const [name, setName] = useState(''); // New state for name
  const [rating, setRating] = useState(0);
  const [review, setReview] = useState('');
  const [message, setMessage] = useState('');
  const [isError, setIsError] = useState(false);

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const feedback = { name, rating, review }; // Include name in the payload
      const response = await submitFeedback(feedback);
      console.log("Feedback submitted successfully:", response);
      setMessage("Thank you for your feedback!");
      setIsError(false);
      setName(''); // Clear name input
      setRating(0);
      setReview('');

      setTimeout(() => {
        navigate('/home');
      }, 1500);
    } catch (error) {
      console.error("Error submitting feedback:", error.message);
      setMessage("Failed to submit feedback. Please try again later.");
      setIsError(true);
    }
  };

  return (
    <section className="bg-light shadow p-4">
      <h3 className="text-center">Submit Your Feedback</h3>
      <Form onSubmit={handleSubmit}>
        {message && <Alert variant={isError ? 'danger' : 'success'}>{message}</Alert>}

        <Form.Group controlId="feedbackName" className="mb-3">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="Enter your name"
            required
          />
        </Form.Group>

        <Form.Group controlId="feedbackRating" className="mb-3">
          <Form.Label>Rating (1 to 5)</Form.Label>
          <Form.Control
            type="number"
            min="1"
            max="5"
            value={rating}
            onChange={(e) => setRating(parseInt(e.target.value, 10))}
            required
          />
        </Form.Group>

        <Form.Group controlId="feedbackReview" className="mb-3">
          <Form.Label>Review</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            value={review}
            onChange={(e) => setReview(e.target.value)}
            placeholder="Share your experience..."
            required
          />
        </Form.Group>

        <Button type="submit" variant="primary" className="w-100">
          Submit Feedback
        </Button>
      </Form>
    </section>
  );
};

export default FeedbackForm;
