package com.anokhin.vending.purchase.dto;

import lombok.Data;

@Data
public class CreatePurchaseRequest {
    private Long vendingMachineId;
    private Long slotId;
    private String cardNumber;
}
