package com.hbs.HBS.controller;

import java.util.List;

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

import com.hbs.HBS.model.Room;
import com.hbs.HBS.service.RoomService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> fetchRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> fetchRoomById(@PathVariable int id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }
    @GetMapping("/roomType/{roomType}")
    public ResponseEntity<List<Room>> fetchRoomsByType(@PathVariable String roomType) {
        List<Room> rooms = roomService.getRoomsByType(roomType);
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    public String addRoom(@Valid @RequestBody Room room) {
        roomService.addRoom(room);
        return "Room added successfully";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable int id) {
        try {
            int rowsAffected = roomService.deleteById(id);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Room Deleted Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room Not Found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting Room");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRoom(@PathVariable int id,@Valid @RequestBody Room room) {
        try {
            room.setRoomId(id);
            int rowsAffected = roomService.updateRoom(room);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Room updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating room");
        }
    }
}

