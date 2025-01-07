package com.dto;

import java.time.LocalDate;

import com.model.Rating;

public class RatingDto {
    private Long userId;
    private Long movieId;
    private int rating;
    private LocalDate ratingDate;
    private String ratingContent;

    // Constructors
    public RatingDto() {
    }

    public RatingDto(Rating alpha) {
        this.userId = alpha.getUser().getId();
        this.movieId = alpha.getMovie().getId();
        this.rating = alpha.getRating();
        this.ratingDate = alpha.getRatingDate();
        this.ratingContent = alpha.getRatingContent();
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(LocalDate ratingDate) {
        this.ratingDate = ratingDate;
    }

    public String getRatingContent() {
        return ratingContent;
    }

    public void setRatingContent(String ratingContent) {
        this.ratingContent = ratingContent;
    }
}
