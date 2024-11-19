package com.service;

import java.util.List;
import java.util.Optional;

import com.model.Category;
import com.model.Movie;
import com.repository.MovieRepository;
import com.repository.CategoryRepository;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    // constructor
    CategoryService(MovieRepository movieRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
    }

    // get all movie in each genre
    public List<Movie> handleGetAllMoviesByGenre(String categoryName) {
        Optional<Category> categoryOpt = categoryRepository.findByCategoryName(categoryName);

        if (categoryOpt.isPresent()) {
            String cateName = categoryOpt.get().getCategoryName();
            return movieRepository.findByCategory_CategoryName(cateName);
        }

        // Return null if the category is not found
        return null;
    }

    // get all categories
    public List<Category> handleGetAllCategory() {
        return this.categoryRepository.findAll();
    }

    public Category handleCreateCategory(Category newCate) {
        Optional<Category> existingCategory = this.categoryRepository.findByCategoryName(newCate.getCategoryName());

        // if it has already in database so we don't create
        if (existingCategory.isPresent()){
            return null;
        }

        // else create
        return this.categoryRepository.save(newCate);
    }

    public Category handleGetCategory(String cate) {
        Optional<Category> targetCate = this.categoryRepository.findByCategoryName(cate);

        // if category is present in database then return it
        if (targetCate.isPresent()) {
            return targetCate.get();
        }

        // else return null
        return null;
    }

    public Category handleUpdateCategory(String category, Category cate) {
        Optional<Category> updateCate = this.categoryRepository.findByCategoryName(category);

        if (updateCate.isPresent()) {
            Category categoryToUpdate = updateCate.get();
            categoryToUpdate.setCategoryName(cate.getCategoryName());
            return this.categoryRepository.save(categoryToUpdate);
        }

        return null;
    }

    public String handleDeleteAllCategory() {
        this.categoryRepository.deleteAllCategory();
        return "All categories have been deleted!";
    }

    public void handleDeleteCategory(Category deleteCate) {
        if (deleteCate != null) {
            this.categoryRepository.deleteByCategoryName(deleteCate.getCategoryName());
        }
    }
}
