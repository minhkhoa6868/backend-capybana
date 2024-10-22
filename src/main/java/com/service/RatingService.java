
package com.service;

import com.model.Rating;
import com.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }
    // truy van 1 danh gia dua vao ID
    public Rating getRatingById(Long id){
        Optional<Rating> ratingOptional = ratingRepository.findById(id);
        if(ratingOptional.isPresent()) {
            return ratingOptional.get();
        } else{
            throw new RuntimeException("Rating not found");
        }
    }

    //tra ve danh sach tat ca rating
   public List<Rating> getAllRatings() {
    return ratingRepository.findAll();
}

}
