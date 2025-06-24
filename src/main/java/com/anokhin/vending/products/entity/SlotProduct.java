package com.anokhin.vending.products.entity;

import com.anokhin.vending.vendingmachine.entity.Slot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "slot_product")
@Getter
@Setter
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

    @Column(nullable = false)
    private int quantity;

    public Product getSlotProduct() {
        return this.quantity > 0 ? this.product : null;
    }

    public Product getProduct() {
        return this.product;
    }
}
