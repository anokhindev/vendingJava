package com.anokhin.vending.products;

import com.anokhin.vending.common.dto.ApiResponse;
import com.anokhin.vending.exception.EntityNotFoundException;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProduct(@PathVariable("id") long id) {
        ApiResponse<Product> response = productService.getProduct(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        ApiResponse<Product> response = productService.updateProduct(id, product);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> deleteProduct(@PathVariable("id") long id) {
        ApiResponse<Product> response = productService.deleteProduct(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/{slotId}/products/{productId}")
    public ResponseEntity<ApiResponse<Product>> addProductToSlot(
            @PathVariable Long slotId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ) {
        ApiResponse<Product> response = productService.addProductTOSlot(slotId, productId, quantity);
        return ResponseEntity.ok(response);
    }
}
