package com.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Wishlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    public Long getId(){ return id;}
    public void setId(Long id){this.id = id;}

    public User getUser(){ return user;}
    public void setUser(User user){ this.user = user;}

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie){this.movie = movie;}

    // constructor
    public Wishlist(){}
    public Wishlist(User user, Movie movie){
        this.user = user;
        this.movie = movie;
    }

    
    
}
