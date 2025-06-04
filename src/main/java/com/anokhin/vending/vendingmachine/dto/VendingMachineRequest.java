package com.anokhin.vending.vendingmachine.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendingMachineRequest {
    private String name;
    private String location;
    private int slotCount;
}