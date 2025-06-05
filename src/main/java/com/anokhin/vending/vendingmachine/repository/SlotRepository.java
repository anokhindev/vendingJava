package com.anokhin.vending.vendingmachine.repository;

import com.anokhin.vending.vendingmachine.dto.SlotResponse;
import com.anokhin.vending.vendingmachine.entity.Slot;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByVendingMachineId(Long vendingMachineId);
    @Modifying
    @Transactional // <-- обязательно
    @Query("DELETE FROM Slot s WHERE s.vendingMachine.id = :vendingMachineId")
    void deleteByVendingMachineId(Long vendingMachineId);
}