package com.hbs.HBS.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hbs.HBS.model.Room;

@Repository
public class RoomRepository {
    private final JdbcTemplate jdbcTemplate;

    public RoomRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Room with the new "photo" column
    private RowMapper<Room> roomRowMapper = (rs, rowNum) -> 
        new Room(
            rs.getInt("roomId"),
            rs.getString("roomNum"),
            rs.getString("roomType"),
            rs.getInt("totalPerson"),
            rs.getDouble("price"),
            rs.getBoolean("isAvailable"),
            rs.getString("photo") // New photo column
        );

    // Create a new room
    public int save(Room room) {
        String sql = "INSERT INTO room (roomNum, roomType, totalPerson, price, isAvailable, photo) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql, 
            room.getRoomNum(),
            room.getRoomType(),
            room.getTotalPerson(),
            room.getPrice(),
            room.isAvailable(),
            room.getPhoto() // Add photo to the save method
        );
    }

    // Read -> Get all available rooms
    public List<Room> findAll() {
        String sql = "SELECT * FROM room WHERE isAvailable = true";
        return jdbcTemplate.query(sql, roomRowMapper);
    }

    // Read -> Get room by Id
    public Room findById(int id) {
        String sql = "SELECT * FROM room WHERE roomId = ?";
        return jdbcTemplate.queryForObject(sql, roomRowMapper, id);
    }

    // Read -> Get rooms by Type
    public List<Room> findByType(String roomType) {
        String sql = "SELECT * FROM room WHERE roomType = ? AND isAvailable = true";
        return jdbcTemplate.query(sql, roomRowMapper, roomType);
    }

    // Update room
    public int update(Room room) {
        String sql = "UPDATE room SET roomNum = ?, roomType = ?, totalPerson = ?, price = ?, isAvailable = ?, photo = ? WHERE roomId = ?";
        return jdbcTemplate.update(
            sql,
            room.getRoomNum(),
            room.getRoomType(),
            room.getTotalPerson(),
            room.getPrice(),
            room.isAvailable(),
            room.getPhoto(), // Add photo to the update method
            room.getRoomId()
        );
    }

    // Delete room by Id
    public int delete(int id) {
        String sql = "DELETE FROM room WHERE roomId = ?";
        return jdbcTemplate.update(sql, id);
    }
}
