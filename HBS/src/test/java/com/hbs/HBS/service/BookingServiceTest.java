package com.hbs.HBS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hbs.HBS.model.Booking;
import com.hbs.HBS.model.BookingDetails;
import com.hbs.HBS.repository.BookingRepository;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        booking = new Booking(1, "john_doe", 101, Date.valueOf("2023-10-01"), Date.valueOf("2023-10-05"), 2, 200.0, "CONFIRMED", new Timestamp(System.currentTimeMillis()), null);
    }

    @Test
    public void testAddBookingSuccessful() {
        when(bookingRepository.isRoomAvailable(anyInt())).thenReturn(true);
        when(bookingRepository.save(any(Booking.class))).thenReturn(1);

        String result = bookingService.addBooking(booking);
        assertEquals("Booking added successfully", result);
        verify(bookingRepository, times(1)).save(booking);
        verify(bookingRepository, times(1)).updateRoomAvailability(booking.getRoomId(), false);
    }

    @Test
    public void testAddBookingRoomNotAvailable() {
        when(bookingRepository.isRoomAvailable(anyInt())).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.addBooking(booking);
        });

        assertEquals("Room is not available for the selected dates", exception.getMessage());
        verify(bookingRepository, times(0)).save(any(Booking.class));
        verify(bookingRepository, times(0)).updateRoomAvailability(anyInt(), anyBoolean());
    }

    @Test
    public void testGetAllBookings() {
        List<Booking> bookings = Arrays.asList(booking);
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookings();
        assertEquals(1, result.size());
        assertEquals(booking, result.get(0));
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookingById() {
        when(bookingRepository.findById(anyInt())).thenReturn(booking);

        Booking result = bookingService.getBookingById(1);
        assertNotNull(result);
        assertEquals(1, result.getBookingId());
        verify(bookingRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testGetFullBookingDetailsByUsername() {
        BookingDetails bookingDetails = new BookingDetails(1, "john_doe", 101, Date.valueOf("2023-10-01"), Date.valueOf("2023-10-05"), 2, 200.0, "CONFIRMED", new Timestamp(System.currentTimeMillis()), null, "101", "Deluxe");
        List<BookingDetails> bookingDetailsList = Arrays.asList(bookingDetails);
        when(bookingRepository.fetchFullBookingDetailsByUsername(anyString())).thenReturn(bookingDetailsList);

        List<BookingDetails> result = bookingService.getFullBookingDetailsByUsername("john_doe");
        assertEquals(1, result.size());
        assertEquals(bookingDetails, result.get(0));
        verify(bookingRepository, times(1)).fetchFullBookingDetailsByUsername(anyString());
    }

    @Test
    public void testUpdateBooking() {
        when(bookingRepository.update(any(Booking.class))).thenReturn(1);

        int result = bookingService.updateBooking(booking);
        assertEquals(1, result);
        verify(bookingRepository, times(1)).update(any(Booking.class));
    }

    @Test
    public void testDeleteById() {
        when(bookingRepository.delete(anyInt())).thenReturn(1);

        int result = bookingService.deleteById(1);
        assertEquals(1, result);
        verify(bookingRepository, times(1)).delete(anyInt());
    }

    @Test
    public void testCheckoutBookingSuccessful() {
        when(bookingRepository.findById(anyInt())).thenReturn(booking);
        when(bookingRepository.update(any(Booking.class))).thenReturn(1);

        boolean result = bookingService.checkoutBooking(1);
        assertTrue(result);
        assertEquals("CLOSED", booking.getBookingStatus());
        verify(bookingRepository, times(1)).findById(anyInt());
        verify(bookingRepository, times(1)).update(any(Booking.class));
        verify(bookingRepository, times(1)).updateRoomAvailability(anyInt(), eq(true));
    }

    @Test
    public void testCheckoutBookingNotFound() {
        when(bookingRepository.findById(anyInt())).thenReturn(null);

        boolean result = bookingService.checkoutBooking(999);
        assertFalse(result);
        verify(bookingRepository, times(1)).findById(anyInt());
        verify(bookingRepository, times(0)).update(any(Booking.class));
        verify(bookingRepository, times(0)).updateRoomAvailability(anyInt(), anyBoolean());
    }
}
