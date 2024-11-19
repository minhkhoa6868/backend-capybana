package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    Movie findByTitle(String title);

    List<Movie> findByCategory_CategoryName(String categoryName);

    @Query(value = "SELECT * FROM movies ORDER BY release_date DESC", nativeQuery = true)
    List<Movie> findAllSortedByNewestDate();
}
