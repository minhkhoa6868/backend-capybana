package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import com.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM Category WHERE category_name = :categoryName", nativeQuery = true)
    Optional<Category> findByCategoryName(@Param("categoryName") String categoryName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Category", nativeQuery = true)
    void deleteAllCategory();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Category WHERE category_name = :categoryName", nativeQuery = true)
    void deleteByCategoryName(@Param("categoryName") String categoryName);
}