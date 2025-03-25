package com.hbs.HBS.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hbs.HBS.model.Room;

public class RoomRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RoomRepository roomRepository;

    private Room room;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room(1, "101", "Deluxe", 2, 150.0, true, "room101.jpg");
    }

    @Test
    public void testFindAll() {
        List<Room> rooms = Arrays.asList(room);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(rooms);

        List<Room> result = roomRepository.findAll();
        assertEquals(1, result.size());
        assertEquals(room, result.get(0));
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void testFindById() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(room);

        Room result = roomRepository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getRoomId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), anyInt());
    }

    @Test
    public void testFindByType() {
        List<Room> rooms = Arrays.asList(room);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any())).thenReturn(rooms);

        List<Room> result = roomRepository.findByType("Deluxe");
        assertNotNull(result);
        assertEquals(1, result.size()); // Ensure the correct number of rooms is returned
        assertEquals("Deluxe", result.get(0).getRoomType()); // Validate room type of first room
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class), any());
    }

    @Test
    public void testDelete() {
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);

        int result = roomRepository.delete(1);
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt());
    }
}