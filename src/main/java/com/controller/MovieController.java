package com.controller;

import org.springframework.web.bind.annotation.RestController;

import com.model.Movie;
import com.service.MovieService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService mvService) {
        this.movieService = mvService;
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> createNewMovie(@RequestBody Movie mvInfo) {
        Movie newMovie = movieService.handleCreateMovie(mvInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMovie);
    }

    // @GetMapping("/movies")
    // public ResponseEntity<List<Movie>> getAllMovies() {
    // return ResponseEntity.status(HttpStatus.OK).body(this.movieService.get)
    // }

}
