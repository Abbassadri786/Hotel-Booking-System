package com.hbs.HBS.service;

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

import com.hbs.HBS.model.Room;
import com.hbs.HBS.repository.RoomRepository;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    private Room room;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room(1, "101", "Deluxe", 2, 150.0, true, "room101.jpg");
    }

    @Test
    public void testAddRoom() {
        when(roomRepository.save(any(Room.class))).thenReturn(1);

        roomService.addRoom(room);

        verify(roomRepository, times(1)).save(room);
    }

    @Test
    public void testGetAllRooms() {
        List<Room> rooms = Arrays.asList(room);
        when(roomRepository.findAll()).thenReturn(rooms);

        List<Room> result = roomService.getAllRooms();
        assertEquals(1, result.size());
        assertEquals(room, result.get(0));
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    public void testGetRoomById() {
        when(roomRepository.findById(anyInt())).thenReturn(room);

        Room result = roomService.getRoomById(1);
        assertNotNull(result);
        assertEquals(1, result.getRoomId());
        verify(roomRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testGetRoomByType() {
        when(roomRepository.findByType(anyString())).thenReturn(room);

        Room result = roomService.getRoomByType("Deluxe");
        assertNotNull(result);
        assertEquals("Deluxe", result.getRoomType());
        verify(roomRepository, times(1)).findByType(anyString());
    }

    @Test
    public void testUpdateRoom() {
        when(roomRepository.update(any(Room.class))).thenReturn(1);

        int result = roomService.updateRoom(room);
        assertEquals(1, result);
        verify(roomRepository, times(1)).update(any(Room.class));
    }

    @Test
    public void testDeleteById() {
        when(roomRepository.delete(anyInt())).thenReturn(1);

        int result = roomService.deleteById(1);
        assertEquals(1, result);
        verify(roomRepository, times(1)).delete(anyInt());
    }
}
