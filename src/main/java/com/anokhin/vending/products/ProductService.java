package com.anokhin.vending.products;

import com.anokhin.vending.common.dto.ApiResponse;
import com.anokhin.vending.exception.EntityNotFoundException;
import com.anokhin.vending.products.entity.Product;
import com.anokhin.vending.products.entity.SlotProduct;
import com.anokhin.vending.products.repository.ProductRepository;
import com.anokhin.vending.products.repository.SlotProductRepository;
import com.anokhin.vending.vendingmachine.entity.Slot;
import com.anokhin.vending.vendingmachine.repository.SlotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final SlotRepository slotRepository;
    private final SlotProductRepository slotProductRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, SlotRepository slotRepository, SlotProductRepository slotProductRepository) {
        this.productRepository = productRepository;
        this.slotRepository = slotRepository;
        this.slotProductRepository = slotProductRepository;
    }

    public ApiResponse<Product> createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        return new ApiResponse<Product>(HttpStatus.CREATED, "Продукт создан", newProduct);
    }

    public ApiResponse<List<Product>> getProducts() {
        List<Product> products = productRepository.findAll();
        return new ApiResponse<>(HttpStatus.OK, "Список продуктов", products);
    }

    public ApiResponse<Product> getProduct(Long productId) throws EntityNotFoundException  {
        Optional<Product> product = productRepository.findById(productId);

        if(product.isEmpty()) {
            throw new EntityNotFoundException("Продукт найден");
        }

        return new ApiResponse<Product>(HttpStatus.OK, "Продукт найден", product.get());
    }


    public ApiResponse<Product> updateProduct(Long productId, Product product) throws EntityNotFoundException  {
        Optional<Product> optionalOldProduct = productRepository.findById(productId);

        if (optionalOldProduct.isEmpty()) {
            throw new EntityNotFoundException("Продукт найден");
        }

        Product oldProduct = optionalOldProduct.get();
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());

        Product updatedProduct = productRepository.save(oldProduct);

        return new ApiResponse<>(HttpStatus.OK, "Продукт обновлён", updatedProduct);
    }


    public ApiResponse<Product> deleteProduct(Long productId) throws EntityNotFoundException  {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()) {
            throw new EntityNotFoundException("Продукт найден");
        }

        productRepository.delete(product.get());
        return new ApiResponse<>(HttpStatus.OK, "Продукт Удален", null);
    }

    public ApiResponse<Product> addProductTOSlot(Long slotId, Long productId, int quantity) throws EntityNotFoundException {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new EntityNotFoundException("Слот не найден"));

        if (slotProductRepository.existsBySlot(slot)) {
            throw new EntityNotFoundException("Слот уже занят другим товаром");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Товар не найден"));

        SlotProduct slotProduct = new SlotProduct();
        slotProduct.setSlot(slot);
        slotProduct.setProduct(product);
        slotProduct.setQuantity(quantity);

        slotProductRepository.save(slotProduct);

        return new ApiResponse<>(HttpStatus.OK, "Слот добавлен", product);
    }
}
