package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Movie;
import com.service.CategoryService;
import com.utils.error.ResInvalidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/movies/category/{category}")
public class MovieCategoryController {

    private final CategoryService categoryService;

    // constructor
    MovieCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovieByCategory(@PathVariable String category) throws ResInvalidException {
        List<Movie> movies = categoryService.handleGetAllMoviesByGenre(category);
        if (movies == null) {
            throw new ResInvalidException("No existing" + category);
        }
        return ResponseEntity.status(HttpStatus.OK).body(movies);
    }

}
