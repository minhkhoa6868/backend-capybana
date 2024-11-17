package com.dto;

public class WishlistDto {
    
    private Long movieId;

    WishlistDto(){}
    WishlistDto(Long movieId){
        this.movieId = movieId;
    }

    public Long getMovieId(){
        return movieId;
    }

    public void setMovieId(Long movieId){
        this.movieId = movieId;
    }
}
