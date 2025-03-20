package com.hbs.HBS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbs.HBS.model.Booking;
import com.hbs.HBS.model.BookingDetails;
import com.hbs.HBS.model.Customer;
import com.hbs.HBS.service.BookingService;
import com.hbs.HBS.service.CustomerService;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Booking booking;
    private Customer customer;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setBookingId(1);
        booking.setUsername("john_doe");
        booking.setConfirmationCode("CONF123");

        customer = new Customer();
        customer.setUsername("john_doe");
        customer.setDob(Date.valueOf(LocalDate.of(2000, 1, 1)));
    }

    @Test
    void testFetchBookings() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(Arrays.asList(booking));

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("john_doe"));
    }

    @Test
    void testFetchFullBookingDetailsByUsername() throws Exception {
        BookingDetails bookingDetails = new BookingDetails(0, null, 0, null, null, 0, 0, null, null, null, null, null);
        when(bookingService.getFullBookingDetailsByUsername("john_doe"))
                .thenReturn(Arrays.asList(bookingDetails));

        mockMvc.perform(get("/api/bookings/full-booking-details/john_doe"))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchBookingById() throws Exception {
        when(bookingService.getBookingById(anyInt())).thenReturn(booking);

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    @Test
    void testAddBooking() throws Exception {
        when(customerService.getCustomerByUsername(any()))
                .thenReturn(Optional.of(customer));
        when(bookingService.addBooking(any())).thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Booking added successfully"))
                .andExpect(jsonPath("$.ConfirmationCode").value("CONF123"));
    }

    @Test
    void testCheckoutBooking() throws Exception {
        when(bookingService.checkoutBooking(anyInt())).thenReturn(true);

        mockMvc.perform(put("/api/bookings/checkout/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking checked out successfully"));
    }

    @Test
    void testUpdateBooking() throws Exception {
        when(bookingService.updateBooking(any())).thenReturn(1);

        mockMvc.perform(put("/api/bookings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking updated successfully"));
    }

    @Test
    void testDeleteBooking() throws Exception {
        when(bookingService.getBookingById(anyInt())).thenReturn(booking);
        when(bookingService.deleteById(anyInt())).thenReturn(1);

        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking Deleted Successfully"));
    }
}
