package com.anokhin.vending.products;

import com.anokhin.vending.common.dto.ApiResponse;
import com.anokhin.vending.products.entity.Product;
import com.anokhin.vending.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ApiResponse<Product> createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        return new ApiResponse<Product>(HttpStatus.CREATED, "Продукт создан", newProduct);
    }

    public ApiResponse<List<Product>> getProducts() {
        List<Product> products = productRepository.findAll();
        return new ApiResponse<>(HttpStatus.OK, "Список продуктов", products);
    }

}
