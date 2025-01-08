package com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.model.Rating;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUserId(Long userId);

    List<Rating> findByMovieId(Long movieId);

    List<Rating> findByRating(int rating);

    @Query("SELECT r FROM Rating r ORDER BY r.ratingDate DESC")
    Page<Rating> findNewestRating(Pageable pageable);
}
