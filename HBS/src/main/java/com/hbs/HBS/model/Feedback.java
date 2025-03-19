package com.hbs.HBS.model;

import java.sql.Timestamp;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Feedback {
    private int id;

    @Min(value = 1, message = "Rating should be at least 1")
    @Max(value = 5, message = "Rating should be at most 5")
    private int rating;

    @NotBlank(message = "Review is required")
    @Size(max = 255, message = "Review should be at most 255 characters long")
    private String review;

    private Timestamp createdAt;
    
    public Feedback() {
    	
    }
    public Feedback(int id, int rating, String review) {
        this.id = id;
        this.rating = rating;
        this.review = review;
    }

    public Feedback(int id, int rating, String review, Timestamp createdAt) {
        this.id = id;
        this.rating = rating;
        this.review = review;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Feedback [id=" + id + ", rating=" + rating + ", review=" + review + ", createdAt=" + createdAt + "]";
    }
}
