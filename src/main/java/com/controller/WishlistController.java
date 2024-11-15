package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;


import com.dto.WishlistDto;
import com.service.WishlistService;
import com.model.Wishlist;


import java.util.List;
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;


    @PostMapping
    public void addToWish(@RequestParam Long userId, @RequestBody WishlistDto wishlistDto){
        wishlistService.addToWish(userId, wishlistDto.getMovieId());
    }

    @GetMapping("/{userId}")
    public List<Wishlist> getUserWishlist(@PathVariable Long userId){
        return wishlistService.getUserWishlist(userId);
    }

}
