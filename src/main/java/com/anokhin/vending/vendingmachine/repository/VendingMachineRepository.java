package com.anokhin.vending.vendingmachine.repository;

import com.anokhin.vending.vendingmachine.entity.VendingMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendingMachineRepository extends JpaRepository<VendingMachine, Long> {

}