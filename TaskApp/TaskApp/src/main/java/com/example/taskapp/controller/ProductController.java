package com.example.taskapp.controller;

import com.example.taskapp.model.Product;
import com.example.taskapp.model.Category;
import com.example.taskapp.service.ProductService;
import com.example.taskapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService; // Inject CategoryService for category validation

    // Get all products with pagination
    @GetMapping
    public ResponseEntity<?> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productService.getAllProducts(pageable);

        // Check if requested page number is greater than total pages
        if (pageable.getPageNumber() >= productPage.getTotalPages()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("totalPages", productPage.getTotalPages());
            errorResponse.put("error", "Page number exceeds total pages");
            return ResponseEntity.status(400).body(errorResponse);
        }

        return ResponseEntity.ok(productPage);
    }





    // Create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // Validate category existence using CategoryService
        Category category = categoryService.getCategoryById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }


    // Delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

}
