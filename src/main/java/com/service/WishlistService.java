package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Movie;
import com.model.User;
import com.model.Wishlist;
import com.repository.MovieRepository;
import com.repository.UserRepository;
import com.repository.WishlistRepository;

import java.util.List;


@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;


    public void addToWish(Long userId, Long movieId){
        User user  = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie  = movieRepository.findById(movieId)
        .orElseThrow(() -> new RuntimeException("Movie not found"));
        
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setMovie(movie);

        wishlistRepository.save(wishlist);
    }

    //get wishlist

    public List<Wishlist> getUserWishlist(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    
        return wishlistRepository.findByUser(user);
    }
    


}
