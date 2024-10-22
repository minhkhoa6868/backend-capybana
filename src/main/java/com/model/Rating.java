package com.model;

import jakarta.persistence.*;

@Entity
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

    // Constructors
    public Rating() {
    }

    public Rating(User user, Movie movie, int rating) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
    }



    //get and set--------------

    //id
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    //user
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }
    //movie
    public Movie getMovie(){
        return movie;
    }
    public void setMovie(Movie movie){
        this.movie = movie;
    }
    //rating
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

}
