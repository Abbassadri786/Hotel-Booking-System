package com.hbs.HBS.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hbs.HBS.model.Customer;

public class CustomerRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1, "john_doe", "john@example.com", "password123", "1234567890", "Male", Date.valueOf("1990-01-01"), "2023-10-01");
    }

    @Test
    public void testSave() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(Date.class)))
            .thenReturn(1);

        int result = customerRepository.save(customer);
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(Date.class));
    }

    @Test
    public void testFindAll() {
        List<Customer> customers = Arrays.asList(customer);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(customers);

        List<Customer> result = customerRepository.findAll();
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void testLogin() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyString(), anyString())).thenReturn(customer);

        Customer result = customerRepository.login("john_doe", "password123");
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), anyString(), anyString());
    }

    @Test
    public void testFindByUsername() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyString())).thenReturn(customer);

        Customer result = customerRepository.findByUsername("john_doe");
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), anyString());
    }

    @Test
    public void testFindById() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(customer);

        Customer result = customerRepository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), anyInt());
    }

    @Test
    public void testUpdate() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(Date.class), anyInt()))
            .thenReturn(1);

        int result = customerRepository.update(customer);
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(Date.class), anyInt());
    }

    @Test
    public void testDelete() {
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);

        int result = customerRepository.delete(1);
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt());
    }
}
