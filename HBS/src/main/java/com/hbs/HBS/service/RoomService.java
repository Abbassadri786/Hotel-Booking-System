package com.hbs.HBS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hbs.HBS.model.Room;
import com.hbs.HBS.repository.RoomRepository;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Add a new room
    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    // Get all rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get room by Id
    public Room getRoomById(int id) {
        return roomRepository.findById(id);
    }
    // Get room by type
    public List<Room> getRoomsByType(String roomType) {
        return roomRepository.findByType(roomType);
    }

    // Update room
    public int updateRoom(Room room) {
        return roomRepository.update(room);
    }

    // Delete room by Id
    public int deleteById(int id) {
        return roomRepository.delete(id);
    }

	
}
