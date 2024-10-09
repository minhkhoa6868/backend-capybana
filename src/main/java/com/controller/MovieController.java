package com.controller;

import org.springframework.web.bind.annotation.RestController;

import com.error.ResInvalidException;
import com.model.Movie;
import com.service.MovieService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService mvService) {
        this.movieService = mvService;
    }

    @PostMapping("/api/movies")
    public ResponseEntity<Movie> createNewMovie(@RequestBody Movie mvInfo) {
        Movie newMovie = movieService.handleCreateMovie(mvInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMovie);
    }

    @DeleteMapping("/api/movies/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") long id) {
        this.movieService.handleDeleteMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }

    @GetMapping("/api/movies/{id}")
    @ResponseBody
    public ResponseEntity<Movie> getMovieByID(@PathVariable("id") long id) throws ResInvalidException {
        if (id > 100) {
            throw new ResInvalidException("id too big");
        }
        Movie resMovie = this.movieService.handleGetMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body(resMovie);
    }

    @GetMapping("/api/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(this.movieService.handleGetAllMovie());
    }

    @PutMapping("/api/movies")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie mv) {
        Movie targetMovie = this.movieService.handleUpdateMovie(mv);
        return ResponseEntity.ok(targetMovie);
    }

}
