package com.service;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Category;
import com.model.Movie;
import com.repository.CategoryRepository;
import com.repository.MovieRepository;

@Service

public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public MovieService(MovieRepository mvRepo) {
        this.movieRepository = mvRepo;
    }

    public Movie handleCreateMovie(Movie newMovie) {
        if (this.movieRepository.findByTitle(newMovie.getTitle()) != null) {
            return null;
        }

        Optional<Category> categoryOptional = categoryRepository.findById(newMovie.getCategory().getId());

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            newMovie.setCategory(category);
        } else {
            throw new NoSuchElementException("Category not found with id: " + newMovie.getCategory().getId());
        }

        return this.movieRepository.save(newMovie);
    }

    public void handleDeleteMovie(long id) {
        this.movieRepository.deleteById(id);
    }

    public Movie handleGetMovie(long id) {
        Optional<Movie> targetMovie = this.movieRepository.findById(id);
        if (targetMovie.isPresent()) {
            return targetMovie.get();
        }
        return null;
    }

    public List<Movie> handleGetAllMovie(Pageable pageable) {
        Page<Movie> getList = this.movieRepository.findAll(pageable);
        return getList.getContent();
    }

    public Movie handleUpdateMovie(Movie targetMovie) {
        Movie movie = this.handleGetMovie(targetMovie.getId());
        if (movie != null) {
            movie.setTitle(targetMovie.getTitle());
            this.movieRepository.save(movie);
        }
        return movie;
    }

    public String handleDeleteAll() {
        movieRepository.deleteAll();
        return "All the movies has been deleted";
    }

}
