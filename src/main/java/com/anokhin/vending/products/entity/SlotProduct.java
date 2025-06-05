package com.anokhin.vending.products.entity;

import com.anokhin.vending.vendingmachine.entity.Slot;
import jakarta.persistence.*;

@Entity
@Table(name = "slot_product")
public class SlotProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    private int quantity;
}
