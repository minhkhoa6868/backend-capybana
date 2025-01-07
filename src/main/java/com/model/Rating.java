package com.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;
    private int rating;
    private LocalDate ratingDate;
    private String ratingContent;

    public Rating(User user2, Movie movie2, int rating2, LocalDate now, String ratingContent2) {
        user = user2;
        movie = movie2;
        rating = rating2;
        ratingDate = now;
        ratingContent = ratingContent2;
    }
}
