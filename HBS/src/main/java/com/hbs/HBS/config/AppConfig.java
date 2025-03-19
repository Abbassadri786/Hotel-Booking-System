package com.hbs.HBS.config;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

import com.hbs.HBS.model.Customer;
import com.hbs.HBS.model.Room;
import com.hbs.HBS.model.Booking;
import com.hbs.HBS.model.Admin;
import com.hbs.HBS.model.Feedback;

@Configuration
@ComponentScan(basePackages = "com.hbs.HBS")
public class AppConfig {

    @Bean
    public Customer customer1() {
        return new Customer("john_doe", "john.doe@example.com", "password123", "1234567890", "Male", Date.valueOf("1990-01-01"));
    }

    @Bean
    public Customer customer2() {
        return new Customer("jane_doe", "jane.doe@example.com", "password456", "0987654321", "Female", Date.valueOf("1992-05-15"));
    }

    @Bean
    public Room room1() {
        return new Room("A 101", "Single", 1, 1000.00, true, "https://example.com/photos/room101.jpg");
    }

    @Bean
    public Room room2() {
        return new Room("A 102", "Double", 2, 1500.00, true, "https://example.com/photos/room102.jpg");
    }

    @Bean
    public Room room3() {
        return new Room("A 103", "Deluxe", 2, 2000.00, true, "https://example.com/photos/room103.jpg");
    }


    @Bean
    public Booking booking1() {
        return new Booking(1, "john_doe", 1, Date.valueOf("2023-04-01"), Date.valueOf("2023-04-05"), 1, 5000.00, "BOOKED", UUID.randomUUID().toString());
    }

    @Bean
    public Booking booking2() {
        return new Booking(2, "jane_doe", 3, Date.valueOf("2023-04-10"), Date.valueOf("2023-04-15"), 2, 10000.00, "BOOKED", UUID.randomUUID().toString());
    }

    @Bean
    public Admin admin1() {
        return new Admin(1, "Admin1", "admin1@example.com", "1122334455", "adminpass1");
    }

    @Bean
    public Admin admin2() {
        return new Admin(2, "Admin2", "admin2@example.com", "2233445566", "adminpass2");
    }

    @Bean
    public Feedback feedback1() {
        return new Feedback(1, 5, "Excellent service!", Timestamp.valueOf(LocalDateTime.now()));
    }

    @Bean
    public Feedback feedback2() {
        return new Feedback(2, 4, "Very good experience.", Timestamp.valueOf(LocalDateTime.now()));
    }

    @Bean
    public Feedback feedback3() {
        return new Feedback(3, 3, "Average stay, could be improved.", Timestamp.valueOf(LocalDateTime.now()));
    }
}
