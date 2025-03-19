package com.hbs.HBS.controller;

import java.sql.Date;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbs.HBS.model.Booking;
import com.hbs.HBS.model.BookingDetails;
import com.hbs.HBS.model.Customer;
import com.hbs.HBS.service.BookingService;
import com.hbs.HBS.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final CustomerService customerService;

    public BookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }
    
    @GetMapping
    public ResponseEntity<List<Booking>> fetchBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/full-booking-details/{username}")
    public ResponseEntity<List<BookingDetails>> fetchFullBookingDetailsByUsername(@PathVariable String username) {
        List<BookingDetails> fullBookingDetails = bookingService.getFullBookingDetailsByUsername(username);
        return ResponseEntity.ok(fullBookingDetails);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Booking> fetchBookingById(@PathVariable int id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addBooking(@Valid @RequestBody Booking booking) {
        Customer customer;
        try {
            customer = customerService.getCustomerByUsername(booking.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Username"));
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error fetching customer details");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Date today = new Date(System.currentTimeMillis());
        Date dob = customer.getDob();
        Period age = Period.between(dob.toLocalDate(), today.toLocalDate());

        if (age.getYears() < 18) {
            throw new IllegalArgumentException("Customer must be 18 years or older to make a booking");
        }

        // Save booking if validation passes
        bookingService.addBooking(booking);

        // Prepare response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Booking added successfully");
        response.put("ConfirmationCode", booking.getConfirmationCode());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/checkout/{id}")
    public ResponseEntity<String> checkoutBooking(@PathVariable int id) {
        try {
            boolean isUpdated = bookingService.checkoutBooking(id);
            if (isUpdated) {
                return ResponseEntity.ok("Booking checked out successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error checking out booking");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable int id, @Valid @RequestBody Booking booking) {
    	try {
    		booking.setBookingId(id);
    		int rowsAffected = bookingService.updateBooking(booking);
    		if (rowsAffected > 0) {
    			return ResponseEntity.ok("Booking updated successfully");
    		} else {
    			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
    		}
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating booking");
    	}
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable int id) {
        try {
            Booking booking = bookingService.getBookingById(id);
            if (booking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking Not Found");
            }
            bookingService.updateRoomAvailability(booking.getRoomId(), true);
            int rowsAffected = bookingService.deleteById(id);

            if (rowsAffected > 0) {
                return ResponseEntity.ok("Booking Deleted Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking Not Found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting Booking");
        }
    }


}
