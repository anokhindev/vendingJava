package com.anokhin.vending.products.repository;

import com.anokhin.vending.products.entity.Product;
import com.anokhin.vending.products.entity.SlotProduct;
import com.anokhin.vending.vendingmachine.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SlotProductRepository extends JpaRepository<SlotProduct, Long> {

    Optional<SlotProduct> findBySlot(Slot slot);

    boolean existsBySlot(Slot slot);

    int countByProduct(Product product);

    boolean existsByProductAndSlot(Product product, Slot slot);

    SlotProduct findByProductAndSlot(Product product, Slot slot);
}