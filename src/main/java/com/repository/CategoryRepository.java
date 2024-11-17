package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);
}