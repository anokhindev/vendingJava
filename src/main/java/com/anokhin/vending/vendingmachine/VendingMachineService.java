package com.anokhin.vending.vendingmachine;

import com.anokhin.vending.common.dto.ApiResponse;
import com.anokhin.vending.exception.EntityNotFoundException;
import com.anokhin.vending.vendingmachine.dto.SlotResponse;
import com.anokhin.vending.vendingmachine.dto.VendingMachineRequest;
import com.anokhin.vending.vendingmachine.dto.VendingMachineResponse;
import com.anokhin.vending.vendingmachine.entity.Slot;
import com.anokhin.vending.vendingmachine.entity.VendingMachine;
import com.anokhin.vending.vendingmachine.repository.SlotRepository;
import com.anokhin.vending.vendingmachine.repository.VendingMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendingMachineService {

    private final VendingMachineRepository vendingMachineRepository;
    private final SlotRepository slotRepository;

    @Autowired
    public VendingMachineService(VendingMachineRepository vendingMachineRepository, SlotRepository slotRepository) {
        this.vendingMachineRepository = vendingMachineRepository;
        this.slotRepository = slotRepository;
    }

    public ApiResponse<VendingMachineResponse> createMachine(VendingMachineRequest request) {
        VendingMachine machine = new VendingMachine();
        machine.setName(request.getName());
        machine.setLocation(request.getLocation());

        if (request.getSlotCount() > 0) {
            for (int i = 0; i < request.getSlotCount(); i++) {
                Slot slot = new Slot();
                slot.setSlotNumber(i);
                slot.setVendingMachine(machine);
                machine.getSlots().add(slot);
            }
        }

        VendingMachine saved = this.vendingMachineRepository.save(machine);

        VendingMachineResponse response = new VendingMachineResponse();
        response.setId(saved.getId());
        response.setLocation(saved.getLocation());
        response.setName(saved.getName());
        response.setSlotCount(saved.getSlots().size());
        return new ApiResponse<>(HttpStatus.CREATED, "Автомат успешно создан", response);
    }

    public ApiResponse<List<VendingMachineResponse>> getAllMachines() {
        List<VendingMachineResponse> machines = this.vendingMachineRepository.findAll().stream()
                .map(vm -> {
                    VendingMachineResponse res = new VendingMachineResponse();
                    res.setId(vm.getId());
                    res.setName(vm.getName());
                    res.setLocation(vm.getLocation());
                    res.setSlotCount(vm.getSlots().size());
                    return res;
                })
                .collect(Collectors.toList());

        return new ApiResponse<>(HttpStatus.OK, "Список автоматов", machines);
    }

    public ApiResponse<VendingMachineResponse> getMachineById(Long id) throws EntityNotFoundException  {
        return this.vendingMachineRepository.findById(id)
                .map(vm -> {
                    VendingMachineResponse res = new VendingMachineResponse();
                    res.setId(vm.getId());
                    res.setName(vm.getName());
                    res.setLocation(vm.getLocation());
                    res.setSlotCount(vm.getSlots().size());
                    return new ApiResponse<>(HttpStatus.OK, "Автомат найден", res);
                })
                .orElseThrow(() -> new EntityNotFoundException("Автомат не найден"));
    }

    public ApiResponse<List<SlotResponse>> getSlotByIdMachine(Long vendingMachineId) {
        List<Slot> slots = slotRepository.findByVendingMachineId(vendingMachineId);
        var result = slots.stream().map(slot -> {

            var response = new SlotResponse();
            response.setId(slot.getId());
            response.setSlotNumber(slot.getSlotNumber());
            response.setCapacity(slot.getCapacity());

            return response;
        }).collect(Collectors.toList());

        return new ApiResponse<>(HttpStatus.OK, "Список слотов по автомату", result);
    }

    public ApiResponse<VendingMachineResponse> removeMachine(Long vendingMachineId) throws EntityNotFoundException  {
        var vendingOptional = vendingMachineRepository.findById(vendingMachineId);

        if (vendingOptional.isEmpty()) {
            throw new EntityNotFoundException("Автомат не найден");
        }

        var vending = vendingOptional.get();

        slotRepository.deleteByVendingMachineId(vendingMachineId);

        VendingMachineResponse response = new VendingMachineResponse();
        response.setId(vending.getId());
        response.setName(vending.getName());
        response.setLocation(vending.getLocation());
        response.setSlotCount(vending.getSlots().size());

        vendingMachineRepository.deleteById(vendingMachineId);

        return new ApiResponse<>(HttpStatus.OK, "Автомат удален", response);
    }
}