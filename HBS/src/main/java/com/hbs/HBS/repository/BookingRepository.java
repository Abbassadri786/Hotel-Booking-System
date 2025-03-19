package com.hbs.HBS.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.hbs.HBS.model.Booking;
import com.hbs.HBS.model.BookingDetails;

@Repository
public class BookingRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Booking
    private RowMapper<Booking> bookingRowMapper = (rs, rowNum) -> 
        new Booking(rs.getInt("bookingId"), rs.getString("username"), rs.getInt("roomId"), 
                    rs.getDate("checkinDate"), rs.getDate("checkoutDate"), rs.getInt("totalPerson"),
                    rs.getDouble("payment"), rs.getString("booking_status"),rs.getTimestamp("created_at"),
                    rs.getString("confirmation_code"));

    // Create a new booking
    public int save(Booking booking) {
        String sql = "INSERT INTO booking (username, roomId, checkinDate, checkoutDate, totalPerson, payment, booking_status, confirmation_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String confirmationCode = UUID.randomUUID().toString();
        return jdbcTemplate.update(sql, booking.getUsername(), booking.getRoomId(), booking.getCheckinDate(), booking.getCheckoutDate(), booking.getTotalPerson(), booking.getPayment(), booking.getBookingStatus(), confirmationCode);
    }

    // Read -> Get All bookings
    public List<Booking> findAll() {
        String sql = "SELECT * FROM booking";
        return jdbcTemplate.query(sql, bookingRowMapper);
    }

    // Read -> Get booking by Id
    public Booking findById(int id) {
        String sql = "SELECT * FROM booking WHERE bookingId = ?";
        return jdbcTemplate.queryForObject(sql, bookingRowMapper, id);
    }

 // Fetch full booking details by username
    public List<BookingDetails> fetchFullBookingDetailsByUsername(String username) {
        String sql = """
            SELECT b.bookingId, b.username, b.roomId, b.checkinDate, b.checkoutDate, 
                   b.totalPerson, b.payment, b.booking_status, b.created_at, b.confirmation_code,
                   r.roomNum, r.roomType
            FROM booking b 
            JOIN room r ON b.roomId = r.roomId 
            WHERE b.username = ?
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new BookingDetails(
                rs.getInt("bookingId"),
                rs.getString("username"),
                rs.getInt("roomId"),
                rs.getDate("checkinDate"),
                rs.getDate("checkoutDate"),
                rs.getInt("totalPerson"),
                rs.getDouble("payment"),
                rs.getString("booking_status"),
                rs.getTimestamp("created_at"),
                rs.getString("confirmation_code"),
                rs.getString("roomNum"),  // Map roomNum directly here
                rs.getString("roomType") // Map roomType directly here
            ), 
            username
        );
    }


    // Update booking
    public int update(Booking booking) {
        String sql = "UPDATE booking SET username = ?, roomId = ?, checkinDate = ?, checkoutDate = ?, totalPerson = ?, payment = ?, booking_status = ?, confirmation_code = ? WHERE bookingId = ?";
        return jdbcTemplate.update(sql, booking.getUsername(), booking.getRoomId(), booking.getCheckinDate(), booking.getCheckoutDate(), booking.getTotalPerson(), booking.getPayment(), booking.getBookingStatus(), booking.getConfirmationCode(), booking.getBookingId());
    }

    // Delete booking by Id
    public int delete(int id) {
        String sql = "DELETE FROM booking WHERE bookingId = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Check if a room is available
    public boolean isRoomAvailable(int roomId) {
        String sql = "SELECT isAvailable FROM room WHERE roomId = ?";
        return jdbcTemplate.queryForObject(sql, Boolean.class, roomId);
    }

    // Update the availability of a room
    public void updateRoomAvailability(int roomId, boolean isAvailable) {
        String sql = "UPDATE room SET isAvailable = ? WHERE roomId = ?";
        jdbcTemplate.update(sql, isAvailable, roomId);
    }
}
