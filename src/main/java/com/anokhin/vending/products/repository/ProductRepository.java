package com.anokhin.vending.products.repository;

import com.anokhin.vending.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
