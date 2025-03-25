package com.hbs.HBS.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hbs.HBS.model.Feedback;

public class FeedbackRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private FeedbackRepository feedbackRepository;

    private Feedback feedback;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        feedback = new Feedback(1, "John Doe", 5, "Great service!", new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testSave() {
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), anyString())).thenReturn(1);
        int result = feedbackRepository.save(feedback);
        assertEquals(1, result);
    }

    @Test
    public void testFindAll() {
        List<Feedback> feedbackList = Arrays.asList(feedback);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(feedbackList);
        List<Feedback> result = feedbackRepository.findAll();
        assertEquals(1, result.size());
        assertEquals(feedback, result.get(0));
    }

    @Test
    public void testFindById() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(feedback);
        Feedback result = feedbackRepository.findById(1);
        assertEquals(feedback, result);
    }

    @Test
    public void testUpdate() {
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), anyString(), anyInt())).thenReturn(1);
        int result = feedbackRepository.update(feedback);
        assertEquals(1, result);
    }
}