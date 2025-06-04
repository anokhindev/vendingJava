package com.anokhin.vending.vendingmachine.dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendingMachineResponse {
    @Id
    private Long id;
    private String name;
    private String location;
    private int slotCount;

    public VendingMachineResponse() {
    }
}
