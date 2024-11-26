package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.service.WishlistService;
import com.model.Wishlist;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;

    // Add a movie to the wishlist
    @PostMapping
    public void addToWish(@RequestParam Long userId, @RequestBody Long movieId) {
       wishlistService.addToWish(userId, movieId);
    }

    // Get the user's wishlist
    @GetMapping("/{userId}")
    public Wishlist getUserWishlist(@PathVariable Long userId) {
        return wishlistService.getUserWishlist(userId);
    }
}