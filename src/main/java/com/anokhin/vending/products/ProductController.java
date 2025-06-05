package com.anokhin.vending.products;

import com.anokhin.vending.common.dto.ApiResponse;
import com.anokhin.vending.products.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product productId) {
        ApiResponse<Product> response = productService.createProduct(productId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        ApiResponse<List<Product>> response = productService.getProducts();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
