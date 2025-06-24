package com.anokhin.vending.purchase.repository;

import com.anokhin.vending.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}