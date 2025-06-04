package com.anokhin.vending.vendingmachine.dto;


import com.anokhin.vending.vendingmachine.entity.VendingMachine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlotResponse {
    private Long id;
    private int slotNumber;
    private int capacity;

    public
    SlotResponse() {

    }
}