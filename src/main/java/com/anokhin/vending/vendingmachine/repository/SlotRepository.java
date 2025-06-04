package com.anokhin.vending.vendingmachine.repository;

import com.anokhin.vending.vendingmachine.dto.SlotResponse;
import com.anokhin.vending.vendingmachine.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByVendingMachineId(Long vendingMachineId);
}