package com.hbs.HBS.model;


import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminTest {

    private final Validator validator;

    public AdminTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidAdmin() {
        Admin admin = new Admin(1, "John Doe", "john.doe@example.com", "1234567890", "password123");

        Set<ConstraintViolation<Admin>> violations = validator.validate(admin);

        assertEquals(0, violations.size(), "Valid Admin object should not have constraint violations");
    }

    @Test
    public void testInvalidAdmin() {
        Admin admin = new Admin(0, "", "invalid-email", "12345", "123");

        Set<ConstraintViolation<Admin>> violations = validator.validate(admin);

        assertEquals(4, violations.size(), "Invalid Admin object should have constraint violations");

        for (ConstraintViolation<Admin> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
