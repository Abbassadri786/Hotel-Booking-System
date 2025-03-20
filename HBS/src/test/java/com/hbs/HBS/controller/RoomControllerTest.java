package com.hbs.HBS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbs.HBS.model.Room;
import com.hbs.HBS.service.RoomService;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @Autowired
    private ObjectMapper objectMapper;

    private Room room;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        room = new Room();
        room.setRoomId(1);
        room.setRoomType("Deluxe");
    }

    @Test
    public void testFetchRooms() throws Exception {
        when(roomService.getAllRooms()).thenReturn(Arrays.asList(room));

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomType").value("Deluxe"));
    }

    @Test
    public void testFetchRoomById() throws Exception {
        when(roomService.getRoomById(anyInt())).thenReturn(room);

        mockMvc.perform(get("/api/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomType").value("Deluxe"));
    }

    @Test
    public void testFetchRoomByType() throws Exception {
        when(roomService.getRoomByType(any())).thenReturn(room);

        mockMvc.perform(get("/api/rooms/roomType/Deluxe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomType").value("Deluxe"));
    }

    @Test
    public void testAddRoom() throws Exception {
        mockMvc.perform(post("/api/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(content().string("Room added successfully"));
    }

    @Test
    public void testDeleteRoom() throws Exception {
        when(roomService.deleteById(anyInt())).thenReturn(1);

        mockMvc.perform(delete("/api/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Room Deleted Successfully"));
    }

    @Test
    public void testUpdateRoom() throws Exception {
        when(roomService.updateRoom(any())).thenReturn(1);

        mockMvc.perform(put("/api/rooms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(content().string("Room updated successfully"));
    }
}
