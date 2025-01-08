package com.service;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dto.Meta;
import com.dto.PaginationData;
import com.model.Category;
import com.model.Movie;
import com.repository.CategoryRepository;
import com.repository.MovieRepository;
import com.turkraft.springfilter.boot.Filter;

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

        Optional<Category> categoryOptional = categoryRepository
                .findByCategoryName(newMovie.getCategory().getCategoryName());

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            newMovie.setCategory(category);
        } else {
            throw new NoSuchElementException(
                    "Category not found with name: " + newMovie.getCategory().getCategoryName());
        }

        return this.movieRepository.save(newMovie);
    }

    public void handleDeleteMovie(long id) {
        this.movieRepository.deleteById(id);
    }

    public Movie handleGetMovie(long id) {
        return this.movieRepository.findById(id);
    }

    public List<Movie> findMovieRatingGreaterThan(float rating) {
        return this.movieRepository.findByMovieRatingGreaterThan(rating);
    }

    public PaginationData handleGetAllMovie(@Filter Specification<Movie> spec, Pageable pageable) {
        Page<Movie> getList = this.movieRepository.findAll(spec, pageable);
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

    public List<Movie> handleGetNewestMovie(Pageable pageable) {
        return this.movieRepository.findAllSortedByNewestDate(pageable);
    }

    public Movie handleUpdateMovie(long id, Movie targetMovie) {
        Movie movie = this.handleGetMovie(id);
        if (movie != null) {
            movie.setTitle(targetMovie.getTitle());
            movie.setDescription(targetMovie.getDescription());
            movie.setReleaseDate(targetMovie.getReleaseDate());
            Optional<Category> categoryOptional = categoryRepository.findById(movie.getCategory().getId());

            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                category.setCategoryName(targetMovie.getCategory().getCategoryName());
                movie.setCategory(category);
            } else {
                throw new NoSuchElementException("Category not found with id: " + targetMovie.getCategory().getId());
            }
            this.movieRepository.save(movie);
        }
        return movie;
    }

    public String handleDeleteAll() {
        this.movieRepository.deleteAll();
        return "All the movies has been deleted";
    }

}
