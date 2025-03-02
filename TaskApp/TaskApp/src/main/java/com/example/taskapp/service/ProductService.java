package com.example.taskapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.taskapp.model.Product;

import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Product createProduct(Product product);
    Optional<Product> getProductById(Long id);  // Add this method
    Product updateProduct(Long id, Product product);  // Add this method
    void deleteProduct(Long id);  // Add this method
}


