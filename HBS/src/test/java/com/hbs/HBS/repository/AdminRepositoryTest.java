package com.hbs.HBS.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hbs.HBS.model.Admin;

public class AdminRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        Admin admin = new Admin(1, "John Doe", "john.doe@example.com", "1234567890", "password");
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any())).thenReturn(1);

        int result = adminRepository.save(admin);

        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any());
    }

    @Test
    public void testExistsByEmail() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyString())).thenReturn(1);

        boolean exists = adminRepository.existsByEmail("john.doe@example.com");

        assertTrue(exists);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class), anyString());
    }

    @Test
    public void testLoginSuccess() {
        Admin admin = new Admin(1, "John Doe", "john.doe@example.com", "1234567890", "password");
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyString(), anyString())).thenReturn(admin);

        Admin result = adminRepository.login("John Doe", "password");

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), anyString(), anyString());
    }

    @Test
    public void testLoginFailure() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyString(), anyString()))
                .thenThrow(new EmptyResultDataAccessException(1));

        Admin result = adminRepository.login("John Doe", "wrongpassword");

        assertNull(result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), anyString(), anyString());
    }
}
