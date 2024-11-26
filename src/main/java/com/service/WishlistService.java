package com.service;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Movie;
import com.model.User;
import com.model.Wishlist;
import com.repository.MovieRepository;
import com.repository.UserRepository;
import com.repository.WishlistRepository;

import java.util.HashSet;


import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public void addToWish(Long userId, Long movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Retrieve or create a new wishlist for the user
        Wishlist wishlist = wishlistRepository.findByUser(user);
        if (wishlist == null) {
            wishlist = new Wishlist(user);
            wishlist.setMovie(new HashSet<>());
        }
        // if the wishlist conta
        /*if(wishlist.getMovies().contains(movie)){
            System.out.println("The movie is already exist");
            return;
        }*/


        // Add movie to the wishlist
        wishlist.getMovies().add(movie);

        // Save the updated wishlist
        wishlistRepository.save(wishlist);
    }

    @Transactional(readOnly = true)
    public Wishlist getUserWishlist(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wishlist wishlist = wishlistRepository.findByUser(user);
        if (wishlist == null) {
            throw new RuntimeException("Wishlist not found for user");
        }

        // Initialize the lazy-loaded movies collection
        wishlist.getMovies().size();

        return wishlist;
    }
}