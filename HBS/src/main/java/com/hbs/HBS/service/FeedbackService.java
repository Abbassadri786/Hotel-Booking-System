package com.hbs.HBS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hbs.HBS.model.Feedback;
import com.hbs.HBS.repository.FeedbackRepository;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Add a new feedback
    public void addFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    // Get All feedbacks
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Get feedback by Id
    public Feedback getFeedbackById(int id) {
        return feedbackRepository.findById(id);
    }

    // Update feedback details
    public int updateFeedback(Feedback feedback) {
        return feedbackRepository.update(feedback);
    }
}