package com.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Wishlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

   // Many-to-many relationship with Movie
    @ManyToMany
    @JoinTable(
        name = "wishlist_movie",
        joinColumns = @JoinColumn(name = "wishlist_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> movies = new HashSet<>();
    
    public Long getId(){ return id;}
    public void setId(Long id){this.id = id;}

    public User getUser(){ return user;}
    public void setUser(User user){ this.user = user;}

    public Set<Movie> getMovies() { return movies; }
    public void setMovie(Set<Movie> movies){this.movies = movies;}

    // constructor
    public Wishlist(){}
    public Wishlist(User user){
        this.user = user; }   
}