package com.anokhin.vending.purchase.entity;

import com.anokhin.vending.products.entity.Product;
import com.anokhin.vending.vendingmachine.entity.Slot;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Slot slot;

    @ManyToOne
    private Product product;

    private String cardNumber;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();

}
