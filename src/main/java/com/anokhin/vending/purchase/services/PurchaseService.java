package com.anokhin.vending.purchase.services;

import com.anokhin.vending.common.dto.ApiResponse;
import com.anokhin.vending.exception.EntityNotFoundException;
import com.anokhin.vending.products.entity.Product;
import com.anokhin.vending.products.entity.SlotProduct;
import com.anokhin.vending.products.repository.SlotProductRepository;
import com.anokhin.vending.purchase.dto.CreatePurchaseRequest;
import com.anokhin.vending.purchase.entity.Purchase;
import com.anokhin.vending.purchase.entity.PurchaseStatus;
import com.anokhin.vending.purchase.repository.PurchaseRepository;
import com.anokhin.vending.purchase.utils.PaymentResult;
import com.anokhin.vending.purchase.utils.SensorCheckResult;
import com.anokhin.vending.vendingmachine.entity.Slot;
import com.anokhin.vending.vendingmachine.entity.VendingMachine;
import com.anokhin.vending.vendingmachine.repository.SlotRepository;
import com.anokhin.vending.vendingmachine.repository.VendingMachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final VendingMachineRepository vendingMachineRepository;
    private final SlotRepository slotRepository;
    private final PurchaseRepository purchaseRepository;
    private final SlotProductRepository slotProductRepository;
    private final PaymentEmulatorService paymentEmulator;
    private final HardwareEmulatorService hardwareEmulator;

    //используем Transactional, всё или ничего, чтобы в базе небыло незавершенных записей
    @Transactional
    public ApiResponse<Purchase> createPurchase(CreatePurchaseRequest request) throws EntityNotFoundException {
        log.info("Начало процесса покупки для запроса: {}", request);
        
        if (request.getCardNumber() == null || request.getCardNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Номер карты не может быть пустым");
        }

        VendingMachine vendingMachine = vendingMachineRepository.findById(request.getVendingMachineId())
                .orElseThrow(() -> new EntityNotFoundException("Вендинг машина не найдена"));

        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new EntityNotFoundException("Слот не найден"));

        if (!slot.getVendingMachine().getId().equals(vendingMachine.getId())) {
            throw new IllegalArgumentException("Слот не принадлежит данной вендинг машине");
        }

        if (!slotProductRepository.existsBySlot(slot)) {
            throw new IllegalStateException("В слоте отсутствует товар");
        }

        SlotProduct slotProduct = slotProductRepository.findBySlot(slot)
                .orElseThrow(() -> new IllegalStateException("Ошибка при получении информации о товаре в слоте"));

        if (slotProduct.getQuantity() <= 0) {
            throw new IllegalStateException("В слоте отсутствует товар");
        }

        Product product = slotProduct.getProduct();
        if (product == null) {
            throw new IllegalStateException("Товар не найден в слоте");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Некорректная цена товара");
        }

        log.info("Создание покупки для товара: {} по цене: {}", product.getName(), product.getPrice());

        // Создаем и сохраняем объект покупки со статусом CREATED
        Purchase purchase = new Purchase();
        purchase.setSlot(slot);
        purchase.setProduct(product);
        purchase.setAmount(product.getPrice());
        purchase.setCardNumber(request.getCardNumber());
        purchase.setStatus(PurchaseStatus.CREATED);
        purchase = purchaseRepository.save(purchase);

        try {
            // Эмулируем эквайринг
            log.info("Начало процесса оплаты для покупки: {}", purchase.getId());
            PaymentResult paymentResult = paymentEmulator.charge(request.getCardNumber(), product.getPrice());

            if (!paymentResult.success()) {
                log.warn("Оплата не прошла для покупки: {}, причина: {}", purchase.getId(), paymentResult.message());
                purchase.setStatus(PurchaseStatus.FAILED);
                purchaseRepository.save(purchase);
                throw new IllegalStateException("Оплата не прошла: " + paymentResult.message());
            }

            log.info("Оплата успешно проведена для покупки: {}", purchase.getId());

            // Эмулируем прокрутку спирали и падение товара
            boolean dispensed = false;
            for (int i = 0; i < 3; i++) {
                log.info("Попытка выдачи товара {} для покупки: {}", i + 1, purchase.getId());
                hardwareEmulator.rotateSlot(slot);
                SensorCheckResult check = hardwareEmulator.checkSensor(slot);
                if (check.success()) {
                    dispensed = true;
                    log.info("Товар успешно выдан для покупки: {}", purchase.getId());
                    break;
                }
                log.warn("Товар не выдан при попытке {} для покупки: {}", i + 1, purchase.getId());
            }

            if (dispensed) {
                // Обновляем количество товара в слоте
                slotProduct.setQuantity(slotProduct.getQuantity() - 1);
                slotProductRepository.save(slotProduct);
                log.info("Количество товара обновлено в слоте: {}", slot.getId());

                purchase.setStatus(PurchaseStatus.SUCCESS);
            } else {
                log.warn("Товар не был выдан, инициируем возврат средств для покупки: {}", purchase.getId());
                purchase.setStatus(PurchaseStatus.REFUND);
                PaymentResult refundResult = paymentEmulator.refund(request.getCardNumber(), product.getPrice());
                if (!refundResult.success()) {
                    log.error("Ошибка при возврате средств для покупки: {}", purchase.getId());
                    throw new IllegalStateException("Ошибка при возврате средств: " + refundResult.message());
                }
                log.info("Средства успешно возвращены для покупки: {}", purchase.getId());
            }

            var saved = purchaseRepository.save(purchase);

            return new ApiResponse<Purchase>(HttpStatus.OK, "Покупка прошла успешно", saved);
        } catch (Exception e) {
            log.error("Ошибка при обработке покупки: {}", purchase.getId(), e);
            purchase.setStatus(PurchaseStatus.FAILED);
            purchaseRepository.save(purchase);
            throw new IllegalStateException("Ошибка при обработке покупки: " + e.getMessage(), e);
        }
    }


}
