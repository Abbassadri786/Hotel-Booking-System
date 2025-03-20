package com.hbs.HBS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbs.HBS.model.Feedback;
import com.hbs.HBS.service.FeedbackService;

@WebMvcTest(FeedbackController.class)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    @Autowired
    private ObjectMapper objectMapper;

    private Feedback feedback;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        feedback = new Feedback();
        feedback.setId(1);
        
    }

    @Test
    public void testFetchFeedbacks() throws Exception {
        when(feedbackService.getAllFeedbacks()).thenReturn(Arrays.asList(feedback));

        mockMvc.perform(get("/api/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comments").value("Great service!"));
    }

    @Test
    public void testFetchFeedbackById() throws Exception {
        when(feedbackService.getFeedbackById(anyInt())).thenReturn(feedback);

        mockMvc.perform(get("/api/feedbacks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments").value("Great service!"));
    }

    @Test
    public void testAddFeedback() throws Exception {
        mockMvc.perform(post("/api/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(feedback)))
                .andExpect(status().isOk())
                .andExpect(content().string("Feedback added successfully"));
    }

    @Test
    public void testUpdateFeedback() throws Exception {
        when(feedbackService.updateFeedback(any())).thenReturn(1);

        mockMvc.perform(put("/api/feedbacks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(feedback)))
                .andExpect(status().isOk())
                .andExpect(content().string("Feedback updated successfully"));
    }
}
