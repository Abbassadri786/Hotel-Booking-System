package com.hbs.HBS.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hbs.HBS.model.Feedback;
import com.hbs.HBS.repository.FeedbackRepository;

class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFeedback() {
        Feedback feedback = new Feedback(1, "John Doe", 5, "Excellent service!", null);
        feedbackService.addFeedback(feedback);
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void testGetAllFeedbacks() {
        Feedback feedback1 = new Feedback(1, "John Doe", 5, "Excellent service!", null);
        Feedback feedback2 = new Feedback(2, "Jane Doe", 4, "Very good experience!", null);
        when(feedbackRepository.findAll()).thenReturn(Arrays.asList(feedback1, feedback2));

        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        assertEquals(2, feedbacks.size());
    }

    @Test
    void testGetFeedbackById() {
        Feedback feedback = new Feedback(1, "John Doe", 5, "Excellent service!", null);
        when(feedbackRepository.findById(1)).thenReturn(feedback);

        Feedback foundFeedback = feedbackService.getFeedbackById(1);
        assertNotNull(foundFeedback);
        assertEquals("John Doe", foundFeedback.getName());
    }

    @Test
    void testUpdateFeedback() {
        Feedback feedback = new Feedback(1, "John Doe", 5, "Excellent service!", null);
        when(feedbackRepository.update(feedback)).thenReturn(1);

        int result = feedbackService.updateFeedback(feedback);
        assertEquals(1, result);
        verify(feedbackRepository, times(1)).update(feedback);
    }
}
