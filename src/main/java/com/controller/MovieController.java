package com.controller;

import org.springframework.web.bind.annotation.RestController;

import com.model.Movie;
import com.service.MovieService;
import com.utils.error.ResInvalidException;
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
        if (newMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newMovie);
    }

    @DeleteMapping("/api/movies/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") long id) throws ResInvalidException {
        if (this.movieService.handleGetMovie(id) == null) {
            throw new ResInvalidException("Movie doesn't exist");
        }
        this.movieService.handleDeleteMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }

    @DeleteMapping("/api/movies/all")
    public ResponseEntity<String> deleteAllMovie() {
        String response = this.movieService.handleDeleteAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/movies/{id}")
    @ResponseBody
    public ResponseEntity<Movie> getMovieByID(@PathVariable("id") long id) throws ResInvalidException {
        Movie resMovie = this.movieService.handleGetMovie(id);
        if (resMovie == null) {
            throw new ResInvalidException("ID doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(resMovie);
    }

    @GetMapping("/api/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(this.movieService.handleGetAllMovie());
    }

    @PutMapping("/api/movies")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie mv) throws ResInvalidException {
        Movie targetMovie = this.movieService.handleUpdateMovie(mv);
        if (targetMovie == null) {
            throw new ResInvalidException("Movies doesn't exist");
        }
        return ResponseEntity.ok(targetMovie);
    }

}
