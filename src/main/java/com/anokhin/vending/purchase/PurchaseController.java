package com.anokhin.vending.purchase;

import com.anokhin.vending.common.dto.ApiResponse;
import com.anokhin.vending.purchase.dto.CreatePurchaseRequest;
import com.anokhin.vending.purchase.entity.Purchase;
import com.anokhin.vending.purchase.services.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<ApiResponse<Purchase>> create(@RequestBody CreatePurchaseRequest request) {
        ApiResponse<Purchase> response = purchaseService.createPurchase(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}