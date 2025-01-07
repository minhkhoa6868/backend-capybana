
package com.service;

import com.model.Rating;
import com.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating createRating(Rating rating) {
        rating.setRatingDate(LocalDateTime.now());
        return ratingRepository.save(rating);
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

    //tra ve danh sach tat ca rating
   public List<Rating> getAllRatings() {
    return ratingRepository.findAll();
}
    // rating theo movieId
    public List<Rating> getRatingsByMovie(Long movieId) {
        return ratingRepository.findByMovieId(movieId);
    }
    //rating theo userId
    public List<Rating> getRatingsByUser(Long userId) {
        return ratingRepository.findByUserId(userId);
    }
}
