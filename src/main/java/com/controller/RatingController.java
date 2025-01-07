package com.controller;

import com.dto.RatingDto;
import com.dto.RatingResponse;
import com.model.Movie;
import com.model.Rating;
import com.model.User;
import com.service.MovieService;
import com.service.RatingService;
import com.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;
    private final MovieService movieService;

    public RatingController(RatingService ratingService, UserService userService, MovieService movieService) {
        this.ratingService = ratingService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody RatingDto ratingDto) {
        User user = userService.fetchUserById(ratingDto.getUserId());
        Movie movie = movieService.handleGetMovie(ratingDto.getMovieId());

        Rating newRating = ratingService
                .createRating(
                        new Rating(user, movie, ratingDto.getRating(), LocalDate.now(), ratingDto.getRatingContent()));
        return ResponseEntity.ok(newRating);
    }

    @GetMapping
    public ResponseEntity<List<RatingDto>> getAllRatings() {
        List<RatingDto> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }
}