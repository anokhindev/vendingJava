package com.anokhin.vending.products;

import com.anokhin.vending.common.dto.ApiResponse;
import com.anokhin.vending.products.entity.Product;
import com.anokhin.vending.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ApiResponse<Product> getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if(product.isEmpty()) {
            return new ApiResponse<Product>(HttpStatus.BAD_REQUEST, "Продукт не найден", null);
        }

        return new ApiResponse<Product>(HttpStatus.OK, "Продукт найден", product.get());
    }


    public ApiResponse<Product> updateProduct(Long productId, Product product) {
        Optional<Product> optionalOldProduct = productRepository.findById(productId);

        if (optionalOldProduct.isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Продукт не найден", null);
        }

        Product oldProduct = optionalOldProduct.get();
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());

        Product updatedProduct = productRepository.save(oldProduct);

        return new ApiResponse<>(HttpStatus.OK, "Продукт обновлён", updatedProduct);
    }
}
