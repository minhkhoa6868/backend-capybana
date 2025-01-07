package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.service.WishlistService;
import com.utils.annotation.ApiMessage;
import com.model.Wishlist;
import com.model.WishlistInput;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    // Add a movie to the wishlist
    @PostMapping
    @ApiMessage("success added to Wishlist")
    public void addToWish(@RequestBody WishlistInput input) {
        wishlistService.addToWish(input.getUserId(), input.getMovieId());
    }

    // Get the user's wishlist
    @GetMapping("/{userId}")
    public Wishlist getUserWishlist(@PathVariable Long userId) {
        return wishlistService.getUserWishlist(userId);
    }
}