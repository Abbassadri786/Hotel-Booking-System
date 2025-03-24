package com.hbs.HBS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hbs.HBS.model.Feedback;
import com.hbs.HBS.repository.FeedbackRepository;

public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    private Feedback feedback;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        feedback = new Feedback(1,"john_doe", 5, "Excellent service", new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testAddFeedback() {
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(1);

        feedbackService.addFeedback(feedback);

        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    public void testGetAllFeedbacks() {
        List<Feedback> feedbacks = Arrays.asList(feedback);
        when(feedbackRepository.findAll()).thenReturn(feedbacks);

        List<Feedback> result = feedbackService.getAllFeedbacks();
        assertEquals(1, result.size());
        assertEquals(feedback, result.get(0));
        verify(feedbackRepository, times(1)).findAll();
    }

    @Test
    public void testGetFeedbackById() {
        when(feedbackRepository.findById(anyInt())).thenReturn(feedback);

        Feedback result = feedbackService.getFeedbackById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(feedbackRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testUpdateFeedback() {
        when(feedbackRepository.update(any(Feedback.class))).thenReturn(1);

        int result = feedbackService.updateFeedback(feedback);
        assertEquals(1, result);
        verify(feedbackRepository, times(1)).update(any(Feedback.class));
    }
}
