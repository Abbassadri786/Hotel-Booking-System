package com.hbs.HBS.repository;
 
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.hbs.HBS.model.Admin;
 
@Repository
public class AdminRepository {
 
    private final JdbcTemplate jdbcTemplate;
 
    public AdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
 
    // RowMapper for Admin
    private RowMapper<Admin> adminRowMapper = (rs, rowNum) -> 
        new Admin(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("mobile"), rs.getString("password"));
 
    // Create a new admin (register)
    public int save(Admin admin) {
        String sql = "INSERT INTO admin (name, email, mobile, password) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, admin.getName(), admin.getEmail(), admin.getMobile(), admin.getPassword());
    }
 
    // Check if an admin exists by email
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM admin WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
 
    // Admin login
    public Admin login(String name, String password) {
        String sql = "SELECT * FROM admin WHERE name = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, adminRowMapper, name, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}