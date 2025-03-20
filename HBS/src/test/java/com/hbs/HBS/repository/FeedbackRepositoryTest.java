package com.hbs.HBS.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
        feedback = new Feedback(1, 5, "Excellent service", new Timestamp(System.currentTimeMillis()));
    }

    // Positive Test Cases

    @Test
    public void testSave() {
        when(jdbcTemplate.update(anyString(), anyInt(), anyString(), any(Timestamp.class)))
            .thenReturn(1);

        int result = feedbackRepository.save(feedback);
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt(), anyString(), any(Timestamp.class));
    }

    @Test
    public void testFindAll() {
        List<Feedback> feedbacks = Arrays.asList(feedback);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(feedbacks);

        List<Feedback> result = feedbackRepository.findAll();
        assertEquals(1, result.size());
        assertEquals(feedback, result.get(0));
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void testFindById() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(feedback);

        Feedback result = feedbackRepository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), anyInt());
    }

    @Test
    public void testUpdate() {
        when(jdbcTemplate.update(anyString(), anyInt(), anyString(), anyInt()))
            .thenReturn(1);

        int result = feedbackRepository.update(feedback);
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt(), anyString(), anyInt());
    }

    // Negative Test Cases

    @Test
    public void testSaveWithInvalidData() {
        when(jdbcTemplate.update(anyString(), anyInt(), anyString(), any(Timestamp.class)))
            .thenThrow(new RuntimeException("Invalid data"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            feedbackRepository.save(new Feedback(0, -1, null, null));
        });

        assertEquals("Invalid data", exception.getMessage());
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt(), anyString(), any(Timestamp.class));
    }

    @Test
    public void testFindByIdNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt()))
            .thenThrow(new RuntimeException("Feedback not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            feedbackRepository.findById(999);
        });

        assertEquals("Feedback not found", exception.getMessage());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), anyInt());
    }

    // Edge Test Cases

    @Test
    public void testFindAllEmpty() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Arrays.asList());

        List<Feedback> result = feedbackRepository.findAll();
        assertTrue(result.isEmpty());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void testUpdateNonExistentFeedback() {
        when(jdbcTemplate.update(anyString(), anyInt(), anyString(), anyInt()))
            .thenReturn(0);

        int result = feedbackRepository.update(new Feedback(999, 3, "Average service", new Timestamp(System.currentTimeMillis())));
        assertEquals(0, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt(), anyString(), anyInt());
    }
}
