package com.service;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dto.Meta;
import com.dto.PaginationData;
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

    public PaginationData handleGetAllMovie(Pageable pageable) {
        Page<Movie> getList = this.movieRepository.findAll(pageable);
        PaginationData data = new PaginationData();
        Meta metaResult = new Meta();
        metaResult.setPageSize(getList.getSize());
        metaResult.setCurrentPage(getList.getNumber() + 1);
        metaResult.setTotalElements(getList.getNumberOfElements());
        metaResult.setTotalPages(getList.getTotalPages());
        data.setMeta(metaResult);
        data.setResult(getList.getContent());
        return data;
    }

    public Movie handleUpdateMovie(long id, Movie targetMovie) {
        Movie movie = this.handleGetMovie(id);
        if (movie != null) {
            // update title
            movie.setTitle(targetMovie.getTitle());

            // update description
            movie.setDescription(targetMovie.getDescription());

            // update release date
            movie.setReleaseDate(targetMovie.getReleaseDate());

            // update category
            Optional<Category> categoryOptional = categoryRepository.findById(movie.getCategory().getId());

            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                category.setId(targetMovie.getCategory().getId());
                movie.setCategory(category);
            } else {
                throw new NoSuchElementException("Category not found with id: " + targetMovie.getCategory().getId());
            }

            // save to database
            this.movieRepository.save(movie);
        }
        return movie;
    }

    public String handleDeleteAll() {
        this.movieRepository.deleteAll();
        return "All the movies has been deleted";
    }

    // list of newest film
    public List<Movie> handleGetNewestMovie() {
        return this.movieRepository.findAllSortedByNewestDate();
    }

}
