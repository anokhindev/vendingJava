package com.anokhin.vending.purchase.servises;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@Service
public class PaymentEmulatorService {
    private final Random random = new Random();

    public boolean processPayment(String cardNumber, BigDecimal amount) {
        log.info("Проверка карты {} на сумму {}", cardNumber, amount);
        return random.nextBoolean();
    }

    public void processRefund(String cardNumber, BigDecimal amount) {
        log.info("Возврат средств по карте {} на сумму {}", cardNumber, amount);
    }
}
