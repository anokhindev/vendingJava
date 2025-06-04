package com.anokhin.vending.vendingmachine;

import com.anokhin.vending.auth.AuthService;
import com.anokhin.vending.common.dto.ApiResponse;
import com.anokhin.vending.vendingmachine.dto.SlotResponse;
import com.anokhin.vending.vendingmachine.dto.VendingMachineRequest;
import com.anokhin.vending.vendingmachine.dto.VendingMachineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vending-machines")
public class VendingMachineController {

    private final VendingMachineService vendingMachineService;

    @Autowired
    public VendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VendingMachineResponse>> createMachine(@RequestBody VendingMachineRequest request) {
        ApiResponse<VendingMachineResponse> response = this.vendingMachineService.createMachine(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VendingMachineResponse>>> getAllMachines() {
        ApiResponse<List<VendingMachineResponse>> response = this.vendingMachineService.getAllMachines();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VendingMachineResponse>> getMachineById(@PathVariable Long id) {
        ApiResponse<VendingMachineResponse> response = this.vendingMachineService.getMachineById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/slots/{id}")
    public ResponseEntity<ApiResponse<List<SlotResponse>>> getSlotByMachineId(@PathVariable Long id) {
        ApiResponse<List<SlotResponse>> slots = vendingMachineService.getSlotByIdMachine(id);
        return ResponseEntity.status(slots.getStatus()).body(slots);
    }
}