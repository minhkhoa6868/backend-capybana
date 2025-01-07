package com.controller;

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
public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
    User user = userService.fetchUserById(rating.getUser().getId());
    Movie movie = movieService.handleGetMovie(rating.getMovie().getId());

    rating.setUser(user);
    rating.setMovie(movie);
    rating.setRatingDate(LocalDateTime.now());

    Rating newRating = ratingService.createRating(rating);

    return ResponseEntity.ok(newRating);
}
    @GetMapping("/{id}")
public ResponseEntity<Rating> getRating(@PathVariable Long id) {
    Rating rating = ratingService.getRatingById(id);
    return ResponseEntity.ok(rating);
}

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

@GetMapping("/movie/{movieId}")
public ResponseEntity<List<Rating>> getRatingsByMovie(@PathVariable Long movieId) {
    List<Rating> ratings = ratingService.getRatingsByMovie(movieId);
    return ResponseEntity.ok(ratings);
}
@GetMapping("/user/{userId}")
public ResponseEntity<List<Rating>> getRatingsByUser(@PathVariable Long userId) {
    List<Rating> ratings = ratingService.getRatingsByUser(userId);
    return ResponseEntity.ok(ratings);
}
}