package com.example.taskapp.service.impl;

import com.example.taskapp.model.Product;
import com.example.taskapp.repository.ProductRepository;
import com.example.taskapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Get all products with pagination
    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    // Create a new product
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Get product by ID
    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Update an existing product
    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setCategory(product.getCategory());
            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    // Delete a product by ID
    @Override
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }
}
