
package com.service;

import com.dto.Meta;
import com.dto.PaginationData;
import com.model.Movie;
import com.model.Rating;
import com.repository.MovieRepository;
import com.repository.RatingRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;

    public RatingService(RatingRepository ratingRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
    }

    public Rating createRating(Rating rating) {
        rating.setRatingDate(LocalDateTime.now());
        Rating newRating = ratingRepository.save(rating);
        Long movieId = newRating.getMovie().getId();
        float newAvg = this.calculateAverageRating(movieId);

        Optional<Movie> optMovie = movieRepository.findById(movieId);
        if (optMovie.isPresent()) {
            Movie movie = optMovie.get();
            movie.setMovieRating(newAvg);
            movieRepository.save(movie);
        }

        return newRating;

    }

    /* Function: calculate average Rating */
    public float calculateAverageRating(Long movieId) {
        List<Rating> listRating = ratingRepository.findByMovieId(movieId);

        if (listRating.isEmpty()) {
            return 0;
        }
        float sum = 0;
        for (Rating r : listRating) {
            sum += r.getRating();
        }
        return sum / listRating.size();
    }

    // truy van 1 danh gia dua vao ID
    public Rating getRatingById(Long id) {
        Optional<Rating> ratingOptional = ratingRepository.findById(id);
        if (ratingOptional.isPresent()) {
            return ratingOptional.get();
        } else {
            throw new RuntimeException("Rating not found");
        }
    }

    public List<Rating> getRatingbyRatings(int rating) {
        List<Rating> ratingList = ratingRepository.findAll();
        Iterator<Rating> iterator = ratingList.iterator();
        while (iterator.hasNext()) {
            Rating currentRating = iterator.next();
            if (currentRating.getRating() < rating) {
                iterator.remove();
            }
        }
        return ratingList;
    }

    public PaginationData handleGetNewRatings(Pageable pageable) {
        Page<Rating> ratingsPage = this.ratingRepository.findNewestRating(pageable);
        PaginationData data = new PaginationData();
        Meta metaResult = new Meta();
        metaResult.setPageSize(ratingsPage.getSize());
        metaResult.setCurrentPage(ratingsPage.getNumber() + 1);
        metaResult.setTotalElements(ratingsPage.getNumberOfElements());
        metaResult.setTotalPages(ratingsPage.getTotalPages());
        data.setMeta(metaResult);
        data.setResult(ratingsPage);
        return data;
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsByMovie(Long movieId) {
        return ratingRepository.findByMovieId(movieId);
    }

    public List<Rating> getRatingsByUser(Long userId) {
        return ratingRepository.findByUserId(userId);
    }
}
