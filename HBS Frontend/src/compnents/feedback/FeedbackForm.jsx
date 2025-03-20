import React, { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import { submitFeedback } from '../utils/ApiFunctions'; // API method
 
const FeedbackForm = () => {
  const [rating, setRating] = useState(0);
  const [review, setReview] = useState('');
  const [message, setMessage] = useState('');
  const [isError, setIsError] = useState(false);
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const feedback = { rating, review };
      const response = await submitFeedback(feedback); // Call API method
      console.log("Feedback submitted successfully:", response);
      setMessage("Thank you for your feedback!");
      setIsError(false);
      setRating(0);
      setReview('');
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