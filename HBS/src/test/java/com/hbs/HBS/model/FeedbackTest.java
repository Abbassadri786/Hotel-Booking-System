package com.hbs.HBS.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.sql.Timestamp;
import java.util.Set;

class FeedbackTest {

    private Feedback feedback;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        feedback = new Feedback(); // Initialize Feedback object
    }

    @Test
    void testGettersAndSetters() {
        feedback.setId(1);
        feedback.setName("John Doe");
        feedback.setRating(5);
        feedback.setReview("Great service!");
        feedback.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        assertEquals(1, feedback.getId());
        assertEquals("John Doe", feedback.getName());
        assertEquals(5, feedback.getRating());
        assertEquals("Great service!", feedback.getReview());
        assertNotNull(feedback.getCreatedAt());
    }

    @Test
    void testValidation() {
        feedback.setName(""); // Invalid name
        feedback.setRating(0); // Invalid rating
        feedback.setReview(""); // Invalid review

        Set<ConstraintViolation<Feedback>> violations = validator.validate(feedback);
        assertEquals(3, violations.size()); // Should have 3 violations

        feedback.setName("Jane Doe");
        feedback.setRating(3);
        feedback.setReview("Good experience.");

        violations = validator.validate(feedback);
        assertTrue(violations.isEmpty()); // Should be valid now
    }
}
