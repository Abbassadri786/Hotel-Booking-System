package com.hbs.HBS.repository;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hbs.HBS.model.Booking;
import com.hbs.HBS.model.BookingDetails;

public class BookingRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookingRepository bookingRepository;

    private Booking booking;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        booking = new Booking(1, "john_doe", 101, Date.valueOf("2023-10-01"), Date.valueOf("2023-10-05"), 2, 200.0, "CONFIRMED", new Timestamp(System.currentTimeMillis()), null);
    }

    
    

    @Test
    public void testUpdate() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), anyInt())).thenReturn(1);

        int result = bookingRepository.update(booking);
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any(), any(), any(), anyInt());
    }

    @Test
    public void testUpdateNonExistentBooking() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), anyInt())).thenReturn(0);

        int result = bookingRepository.update(new Booking(999, "Jane_Doe", 5, Date.valueOf("2023-10-01"), Date.valueOf("2023-10-05"), 1, 100.0, "CONFIRMED", new Timestamp(System.currentTimeMillis()), null));
        assertEquals(0, result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any(), any(), any(), anyInt());
    }

    @Test
    public void testFindAll() {
        List<Booking> bookings = Arrays.asList(booking);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(bookings);

        List<Booking> result = bookingRepository.findAll();
        assertEquals(1, result.size());
        assertEquals(booking, result.get(0));
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void testFindById() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(booking);

        Booking result = bookingRepository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getBookingId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), anyInt());
    }

   
    @Test
    public void testDelete() {
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);

        int result = bookingRepository.delete(1);
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt());
    }

    @Test
    public void testIsRoomAvailable() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Boolean.class), anyInt())).thenReturn(true);

        boolean result = bookingRepository.isRoomAvailable(101);
        assertTrue(result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Boolean.class), eq(101));
    }

    @Test
    public void testUpdateRoomAvailability() {
        when(jdbcTemplate.update(anyString(), eq(false), eq(101))).thenReturn(1);

        bookingRepository.updateRoomAvailability(101, false);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(false), eq(101));
    }
}
