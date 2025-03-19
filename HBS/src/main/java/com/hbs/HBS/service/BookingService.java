package com.hbs.HBS.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.hbs.HBS.model.Booking;
import com.hbs.HBS.model.BookingDetails;
import com.hbs.HBS.repository.BookingRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Add a new booking
    public String addBooking(Booking booking) {
        if (isRoomAvailable(booking.getRoomId())) {
            bookingRepository.save(booking);
            updateRoomAvailability(booking.getRoomId(), false);
            return "Booking added successfully";
        } else {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get booking by Id
    public Booking getBookingById(int id) {
        return bookingRepository.findById(id);
    }

    // Get full booking details by username
    public List<BookingDetails> getFullBookingDetailsByUsername(String username) {
        return bookingRepository.fetchFullBookingDetailsByUsername(username);
    }


    // Update booking
    public int updateBooking(Booking booking) {
        return bookingRepository.update(booking);
    }

    // Delete booking by Id
    public int deleteById(int id) {
        return bookingRepository.delete(id);
    }

    // Check if room is available
    private boolean isRoomAvailable(int roomId) {
        return bookingRepository.isRoomAvailable(roomId);
    }
    public boolean checkoutBooking(int id) {
        Booking booking = bookingRepository.findById(id);
        if (booking != null) {
            booking.setBookingStatus("CLOSED");
            bookingRepository.update(booking);
            updateRoomAvailability(booking.getRoomId(), true);
            return true;
        } else {
            return false;
        }
    }

    // Update room availability status
    public void updateRoomAvailability(int roomId, boolean isAvailable) {
        bookingRepository.updateRoomAvailability(roomId, isAvailable);
    }
}
