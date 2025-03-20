package com.hbs.HBS.model;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminRegistrationRequestTest {

    private final Validator validator;

    public AdminRegistrationRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidAdminRegistrationRequest() {
        Admin validAdmin = new Admin(1, "Jane Doe", "jane.doe@example.com", "9876543210", "securePassword");
        AdminRegistrationRequest request = new AdminRegistrationRequest();
        request.setAccessCode("12345");
        request.setAdmin(validAdmin);

        Set<ConstraintViolation<AdminRegistrationRequest>> violations = validator.validate(request);

        assertEquals(0, violations.size(), "Valid AdminRegistrationRequest should not have constraint violations");
    }

    @Test
    public void testInvalidAdminRegistrationRequest() {
        Admin invalidAdmin = new Admin(0, "", "invalid-email", "123", "123"); // Invalid Admin
        AdminRegistrationRequest request = new AdminRegistrationRequest();
        request.setAccessCode(""); // Blank access code
        request.setAdmin(invalidAdmin);

        Set<ConstraintViolation<AdminRegistrationRequest>> violations = validator.validate(request);

        // Expecting violations for accessCode and the fields of Admin
        assertEquals(5, violations.size(), "Invalid AdminRegistrationRequest should have constraint violations");

        for (ConstraintViolation<AdminRegistrationRequest> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
