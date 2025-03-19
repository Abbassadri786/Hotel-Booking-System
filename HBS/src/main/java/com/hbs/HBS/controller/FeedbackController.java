package com.hbs.HBS.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbs.HBS.model.Feedback;
import com.hbs.HBS.service.FeedbackService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> fetchFeedbacks() {
    	List<Feedback> feedbacks  = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> fetchFeedbackById(@PathVariable int id) {
        Feedback feedback = feedbackService.getFeedbackById(id);
        return ResponseEntity.ok(feedback);
    }

    @PostMapping
    public String addFeedback(@Valid @RequestBody Feedback feedback) {
        feedbackService.addFeedback(feedback);
        return "Feedback added successfully";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFeedback(@PathVariable int id, @Valid @RequestBody Feedback feedback) {
        try {
            feedback.setId(id);
            int rowsAffected = feedbackService.updateFeedback(feedback);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Feedback updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feedback not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating feedback");
        }
    }
}