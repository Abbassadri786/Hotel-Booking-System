package com.hbs.HBS.model;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.sql.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    private final Validator validator;

    public CustomerTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidCustomer() {
        Customer validCustomer = new Customer(
            1,
            "john_doe",
            "john.doe@example.com",
            "securePassword123",
            "9876543210",
            "Male",
            Date.valueOf("1990-01-01"),
            "2025-03-18 10:15:55"
        );

        Set<ConstraintViolation<Customer>> violations = validator.validate(validCustomer);

        assertEquals(0, violations.size(), "Valid Customer object should not have constraint violations");
    }

    @Test
    public void testInvalidCustomer() {
        Customer invalidCustomer = new Customer(
            0,
            "", // Blank username
            "invalid-email", // Invalid email
            "123", // Password too short
            "123", // Invalid phone number
            "", // Gender not provided
            null, // Null date of birth
            null // Null createdAt
        );

        Set<ConstraintViolation<Customer>> violations = validator.validate(invalidCustomer);

        // Check for violations
        assertEquals(5, violations.size(), "Invalid Customer object should have constraint violations");

        for (ConstraintViolation<Customer> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    public void testSettersAndGetters() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setUsername("jane_doe");
        customer.setEmail("jane.doe@example.com");
        customer.setPassword("SecurePass123");
        customer.setPhone("9876543210");
        customer.setGender("Female");
        customer.setDob(Date.valueOf("1992-03-15"));
        customer.setCreatedAt("2025-03-18 10:15:55");

        assertEquals(1, customer.getId());
        assertEquals("jane_doe", customer.getUsername());
        assertEquals("jane.doe@example.com", customer.getEmail());
        assertEquals("SecurePass123", customer.getPassword());
        assertEquals("9876543210", customer.getPhone());
        assertEquals("Female", customer.getGender());
        assertEquals(Date.valueOf("1992-03-15"), customer.getDob());
        assertEquals("2025-03-18 10:15:55", customer.getCreatedAt());
    }

    @Test
    public void testToString() {
        Customer customer = new Customer(
            1,
            "john_doe",
            "john.doe@example.com",
            "securePassword123",
            "9876543210",
            "Male",
            Date.valueOf("1990-01-01"),
            "2025-03-18 10:15:55"
        );

        String expected = "Customer [id=1, username=john_doe, email=john.doe@example.com, password=securePassword123, phone=9876543210, gender=Male, dob=1990-01-01, createdAt=2025-03-18 10:15:55]";
        assertEquals(expected, customer.toString());
    }
}
