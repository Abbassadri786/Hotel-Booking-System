package com.hbs.HBS.model;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {

    private final Validator validator;

    public BookingTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidBooking() {
        Booking validBooking = new Booking(
            1,
            "user123",
            101,
            Date.valueOf("2025-03-20"),
            Date.valueOf("2025-03-25"),
            2,
            5000.0,
            "BOOKED",
            "123e4567-e89b-12d3-a456-426614174000"
        );

        Set<ConstraintViolation<Booking>> violations = validator.validate(validBooking);

        assertEquals(0, violations.size(), "Valid Booking object should not have constraint violations");
    }

    @Test
    public void testInvalidBooking() {
        Booking invalidBooking = new Booking(
            0,
            "",
            -1,
            null,
            null,
            -3,
            -5000.0,
            "",
            "short-confirmation-code"
        );

        Set<ConstraintViolation<Booking>> violations = validator.validate(invalidBooking);

        // Expecting multiple constraint violations
        assertEquals(6, violations.size(), "Invalid Booking object should have constraint violations");

        for (ConstraintViolation<Booking> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}

