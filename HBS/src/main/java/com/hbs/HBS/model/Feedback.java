package com.hbs.HBS.model;

import java.sql.Timestamp;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Feedback {
    private int id;

    @NotBlank(message = "Name is required") // Validation for name
    @Size(max = 255, message = "Name should be at most 255 characters long")
    private String name; // New field for name

    @Min(value = 1, message = "Rating should be at least 1")
    @Max(value = 5, message = "Rating should be at most 5")
    private int rating;

    @NotBlank(message = "Review is required")
    @Size(max = 255, message = "Review should be at most 255 characters long")
    private String review;

    private Timestamp createdAt;

    public Feedback() {
    }

    public Feedback(int id, String name, int rating, String review) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.review = review;
    }

    public Feedback(int id, String name, int rating, String review, Timestamp createdAt) {
        this.id = id;
        this.name = name;
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

    public String getName() { // Getter for name
        return name;
    }

    public void setName(String name) { // Setter for name
        this.name = name;
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
        return "Feedback [id=" + id + ", name=" + name + ", rating=" + rating + ", review=" + review + ", createdAt="
                + createdAt + "]";
    }
}
