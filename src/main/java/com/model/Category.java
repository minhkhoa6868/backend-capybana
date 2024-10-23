package com.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String category;

    // @OneToMany (mappedBy = "category")
    // @JsonIgnore
    // private List<Movie> movies;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // public List<Movie> getMovies() {
    //     return movies;
    // }

    // public void setMovies(List<Movie> movies) {
    //     this.movies = movies;
    // }
}
