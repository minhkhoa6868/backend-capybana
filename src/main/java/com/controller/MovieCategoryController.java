package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Category;
import com.model.Movie;
import com.service.CategoryService;
import com.utils.annotation.ApiMessage;
import com.utils.error.ResInvalidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/movies/category")
public class MovieCategoryController {

    private final CategoryService categoryService;

    // constructor
    MovieCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ApiMessage("Get all category success!")
    public ResponseEntity<List<Category>> getAllCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(this.categoryService.handleGetAllCategory());
    }

    @PostMapping
    @ApiMessage("Create category success!")
    public ResponseEntity<Category> createNewCategory(@RequestBody Category cate) {
        Category newCate = categoryService.handleCreateCategory(cate);

        if (newCate == null) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newCate);
    }
    
    @PutMapping("{category}")
    public ResponseEntity<Category> updateCategory(@PathVariable String category, @RequestBody Category cate) throws ResInvalidException {
        Category updateCategory = categoryService.handleUpdateCategory(category, cate);

        if (updateCategory == null) {
            throw new ResInvalidException("Category does not exist");
        }

        return ResponseEntity.ok(updateCategory);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllCategory() {
        String response = this.categoryService.handleDeleteAllCategory();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{category}")
    public ResponseEntity<String> deleteCategory(@PathVariable String category) throws ResInvalidException {
        Category deleteCategory = this.categoryService.handleGetCategory(category);

        if (deleteCategory == null) {
            throw new ResInvalidException("Category does not exist"); 
        }

        System.out.println(deleteCategory.getId());

        this.categoryService.handleDeleteCategory(deleteCategory);

        return ResponseEntity.status(HttpStatus.OK).body(category + " has been deleted");
    }
}
