package com.hbs.HBS.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hbs.HBS.model.Customer;

@Repository
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Customer
    private RowMapper<Customer> customerRowMapper = (rs, rowNum) -> 
    new Customer(rs.getInt("id"), rs.getString("username"), rs.getString("email"), 
                 rs.getString("password"), rs.getString("phone"), rs.getString("gender"),
                 rs.getDate("dob"), rs.getString("created_at"));

    // Create a new customer
    public int save(Customer customer) {
        String sql = "INSERT INTO customer (username, email, password, phone, gender, dob) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, customer.getUsername(), customer.getEmail(), customer.getPassword(), customer.getPhone(), customer.getGender(), customer.getDob());
    }

    // Read -> Get All customers
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, customerRowMapper);
    }
    // Customer login
    public Customer login(String username, String password) {
        String sql = "SELECT * FROM customer WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, customerRowMapper, username, password);
    }
    // Read -> Get customer by username
    public Customer findByUsername(String username) {
        String sql = "SELECT * FROM customer WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, customerRowMapper, username);
    }

    // Read -> Get customer by Id
    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, customerRowMapper, id);
    }

    // Update customer
    public int update(Customer customer) {
        String sql = "UPDATE customer SET username = ?, email = ?, password = ?, phone = ?, gender = ?, dob = ? WHERE id = ?";
        return jdbcTemplate.update(sql, customer.getUsername(), customer.getEmail(), customer.getPassword(), customer.getPhone(), customer.getGender(), customer.getDob(), customer.getId());
    }

    // Delete customer by Id
    public int delete(int id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
