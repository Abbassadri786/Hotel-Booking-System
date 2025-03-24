package com.hbs.HBS.model;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.sql.Timestamp;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeedbackTest {

    private final Validator validator;

    public FeedbackTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidFeedback() {
        Feedback validFeedback = new Feedback(
            1,"john_doe",
            5, // Valid rating
            "This was a great service!", // Valid review
            Timestamp.valueOf("2025-03-18 12:00:00") // Created at
        );

        Set<ConstraintViolation<Feedback>> violations = validator.validate(validFeedback);

        assertEquals(0, violations.size(), "Valid Feedback object should not have constraint violations");
    }

    @Test
    public void testInvalidFeedback() {
        Feedback invalidFeedback = new Feedback(
            0,"john_doe", // Invalid ID (not validated explicitly here)
            6, // Invalid rating (greater than 5)
            "", // Blank review
            null // Null createdAt
        );

        Set<ConstraintViolation<Feedback>> violations = validator.validate(invalidFeedback);

        // Expecting multiple constraint violations
        assertEquals(2, violations.size(), "Invalid Feedback object should have constraint violations");

        for (ConstraintViolation<Feedback> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    public void testSettersAndGetters() {
        Feedback feedback = new Feedback(1,"john_doe", 4, "Good experience!");
        feedback.setRating(3);
        feedback.setReview("Average experience.");
        feedback.setCreatedAt(Timestamp.valueOf("2025-03-18 15:30:00"));

        assertEquals(1, feedback.getId());
        assertEquals(3, feedback.getRating());
        assertEquals("Average experience.", feedback.getReview());
        assertEquals(Timestamp.valueOf("2025-03-18 15:30:00"), feedback.getCreatedAt());
    }

    @Test
    public void testToString() {
        Feedback feedback = new Feedback(
            1,"john_doe",
            5,
            "Excellent service!",
            Timestamp.valueOf("2025-03-18 12:00:00")
        );

        String expected = "Feedback [id=1, rating=5, review=Excellent service!, createdAt=2025-03-18 12:00:00.0]";
        assertEquals(expected, feedback.toString());
    }
}
