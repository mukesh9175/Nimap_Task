package com.example.taskapp.service;

import com.example.taskapp.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<Category> getAllCategories(Pageable pageable);
    Category createCategory(Category category);
    Optional<Category> getCategoryById(Long id);
    Optional<Category> updateCategory(Long id, Category category);  // FIX RETURN TYPE
    void deleteCategory(Long id);
}
