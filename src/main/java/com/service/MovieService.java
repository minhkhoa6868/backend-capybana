package com.service;

import org.springframework.stereotype.Service;

import com.model.Movie;
import com.repository.MovieRepository;

@Service

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository mvRepo) {
        this.movieRepository = mvRepo;
    }

    public Movie handleCreateMovie(Movie newMovie) {
        return this.movieRepository.save(newMovie);
    }
}
