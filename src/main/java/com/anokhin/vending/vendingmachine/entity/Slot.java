package com.anokhin.vending.vendingmachine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int slotNumber;
    private int capacity;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vending_machine_id")
    private VendingMachine vendingMachine;

    public Slot() {
    }

    public Slot(int slotNumber, int capacity, VendingMachine vendingMachine) {
        this.slotNumber = slotNumber;
        this.capacity = capacity;
        this.vendingMachine = vendingMachine;
    }


}