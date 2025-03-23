package com.hbs.HBS.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hbs.HBS.model.Feedback;

@Repository
public class FeedbackRepository {

    private final JdbcTemplate jdbcTemplate;

    public FeedbackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Feedback
    private RowMapper<Feedback> feedbackRowMapper = (rs, rowNum) ->
        new Feedback(rs.getInt("id"), rs.getString("name"), rs.getInt("rating"), rs.getString("review"), rs.getTimestamp("created_at"));

    // Create a new feedback
    public int save(Feedback feedback) {
        String sql = "INSERT INTO feedback (name, rating, review) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, feedback.getName(), feedback.getRating(), feedback.getReview());
    }

    // Read -> Get All feedbacks
    public List<Feedback> findAll() {
        String sql = "SELECT * FROM feedback";
        return jdbcTemplate.query(sql, feedbackRowMapper);
    }

    // Read -> Get feedback by Id
    public Feedback findById(int id) {
        String sql = "SELECT * FROM feedback WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, feedbackRowMapper, id);
    }

    // Update feedback
    public int update(Feedback feedback) {
        String sql = "UPDATE feedback SET name = ?, rating = ?, review = ? WHERE id = ?";
        return jdbcTemplate.update(sql, feedback.getName(), feedback.getRating(), feedback.getReview(), feedback.getId());
    }
}
