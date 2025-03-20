
package com.hbs.HBS.model;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDetailsTest {

    @Test
    public void testConstructorAndGetters() {
        // Prepare test data
        int bookingId = 1;
        String username = "user123";
        int roomId = 101;
        Date checkinDate = Date.valueOf("2025-03-20");
        Date checkoutDate = Date.valueOf("2025-03-25");
        int totalPerson = 2;
        double payment = 5000.0;
        String bookingStatus = "BOOKED";
        Timestamp createdAt = Timestamp.valueOf("2025-03-18 10:15:55");
        String confirmationCode = "123e4567-e89b-12d3-a456-426614174000";
        String roomNum = "A101";
        String roomType = "Deluxe";

        // Create object
        BookingDetails bookingDetails = new BookingDetails(
                bookingId, username, roomId, checkinDate, checkoutDate,
                totalPerson, payment, bookingStatus, createdAt, confirmationCode,
                roomNum, roomType
        );

        // Assert all values
        assertEquals(bookingId, bookingDetails.getBookingId());
        assertEquals(username, bookingDetails.getUsername());
        assertEquals(roomId, bookingDetails.getRoomId());
        assertEquals(checkinDate, bookingDetails.getCheckinDate());
        assertEquals(checkoutDate, bookingDetails.getCheckoutDate());
        assertEquals(totalPerson, bookingDetails.getTotalPerson());
        assertEquals(payment, bookingDetails.getPayment(), 0.0);
        assertEquals(bookingStatus, bookingDetails.getBookingStatus());
        assertEquals(createdAt, bookingDetails.getCreatedAt());
        assertEquals(confirmationCode, bookingDetails.getConfirmationCode());
        assertEquals(roomNum, bookingDetails.getRoomNum());
        assertEquals(roomType, bookingDetails.getRoomType());
    }

    @Test
    public void testSetters() {
        // Create object
        BookingDetails bookingDetails = new BookingDetails(
                0, null, 0, null, null,
                0, 0.0, null, null, null,
                null, null
        );

        // Prepare test data
        bookingDetails.setBookingId(1);
        bookingDetails.setUsername("user123");
        bookingDetails.setRoomId(101);
        bookingDetails.setCheckinDate(Date.valueOf("2025-03-20"));
        bookingDetails.setCheckoutDate(Date.valueOf("2025-03-25"));
        bookingDetails.setTotalPerson(2);
        bookingDetails.setPayment(5000.0);
        bookingDetails.setBookingStatus("BOOKED");
        bookingDetails.setCreatedAt(Timestamp.valueOf("2025-03-18 10:15:55"));
        bookingDetails.setConfirmationCode("123e4567-e89b-12d3-a456-426614174000");
        bookingDetails.setRoomNum("A101");
        bookingDetails.setRoomType("Deluxe");

        // Assert all values
        assertEquals(1, bookingDetails.getBookingId());
        assertEquals("user123", bookingDetails.getUsername());
        assertEquals(101, bookingDetails.getRoomId());
        assertEquals(Date.valueOf("2025-03-20"), bookingDetails.getCheckinDate());
        assertEquals(Date.valueOf("2025-03-25"), bookingDetails.getCheckoutDate());
        assertEquals(2, bookingDetails.getTotalPerson());
        assertEquals(5000.0, bookingDetails.getPayment(), 0.0);
        assertEquals("BOOKED", bookingDetails.getBookingStatus());
        assertEquals(Timestamp.valueOf("2025-03-18 10:15:55"), bookingDetails.getCreatedAt());
        assertEquals("123e4567-e89b-12d3-a456-426614174000", bookingDetails.getConfirmationCode());
        assertEquals("A101", bookingDetails.getRoomNum());
        assertEquals("Deluxe", bookingDetails.getRoomType());
    }

    @Test
    public void testToString() {
        // Prepare test data
        BookingDetails bookingDetails = new BookingDetails(
                1, "user123", 101, Date.valueOf("2025-03-20"), Date.valueOf("2025-03-25"),
                2, 5000.0, "BOOKED", Timestamp.valueOf("2025-03-18 10:15:55"),
                "123e4567-e89b-12d3-a456-426614174000", "A101", "Deluxe"
        );

        String expected = "BookingDetails [bookingId=1, username=user123, roomId=101, checkinDate=2025-03-20, " +
                "checkoutDate=2025-03-25, totalPerson=2, payment=5000.0, bookingStatus=BOOKED, createdAt=2025-03-18 10:15:55.0, " +
                "confirmationCode=123e4567-e89b-12d3-a456-426614174000, roomNum=A101, roomType=Deluxe]";

        assertEquals(expected, bookingDetails.toString());
    }
}
