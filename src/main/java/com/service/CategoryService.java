package com.service;

import java.util.List;
import java.util.Optional;

import com.model.Category;
import com.model.Movie;
import com.repository.MovieRepository;
import com.repository.CategoryRepository;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    // constructor
    CategoryService(MovieRepository movieRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
    }

    // get all movie in each genre
    public List<Movie> handleGetAllMoviesByGenre(String categoryName) {
        Optional<Category> categoryOpt = categoryRepository.findByCategory(categoryName);

        if (categoryOpt.isPresent()) {
            Long categoryId = categoryOpt.get().getId();
            return movieRepository.findByCategory_Id(categoryId);
        }

        // Return an empty list if the category is not found
        return null;
    }
}
