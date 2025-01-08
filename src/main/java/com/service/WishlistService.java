package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Movie;
import com.model.User;
import com.model.Wishlist;
import com.repository.MovieRepository;
import com.repository.UserRepository;
import com.repository.WishlistRepository;
import com.utils.error.ResInvalidException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

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

        Wishlist wishlist = wishlistRepository.findByUser(user);
        if (wishlist == null) {
            wishlist = new Wishlist(user);
            wishlist.setMovie(new HashSet<>());
        }
        wishlist.getMovies().add(movie);

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
        wishlist.getMovies().size();
        return wishlist;
    }

    @Transactional
    public boolean deleteWishlist(long userId, long movieId) throws ResInvalidException {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResInvalidException("No existing user");
        }
        Wishlist wishlist = wishlistRepository.findByUser(user);
        if (wishlist == null) {
            throw new ResInvalidException("Wishlist not found for user");
        }
        Movie movie = movieRepository.findById(movieId);
        if (movie == null) {
            throw new ResInvalidException("Movie not found");
        }
        Iterator<Movie> iterator = wishlist.getMovies().iterator();
        boolean movieRemoved = false;
        while (iterator.hasNext()) {
            Movie currentMovie = iterator.next();
            if (currentMovie.equals(movie)) {
                iterator.remove();
                movieRemoved = true;
                break;
            }
        }
        if (!movieRemoved) {
            throw new ResInvalidException("Movie not found in wishlist");
        }
        wishlistRepository.save(wishlist);
        return true;
    }

}