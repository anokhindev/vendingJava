package com.anokhin.vending.purchase.services;

import com.anokhin.vending.purchase.utils.PaymentResult;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Random;

@Service
public class PaymentEmulatorService {
    private final Random random = new Random();

    public PaymentResult charge(String cardNumber, BigDecimal amount) {
        // Эмуляция обработки платежа
        // В реальном приложении здесь был бы вызов платежного шлюза
        boolean success = random.nextDouble() > 0.1; // 90% успешных платежей
        
        if (success) {
            return new PaymentResult(true, "Payment successful");
        } else {
            return new PaymentResult(false, "Payment failed");
        }
    }

    public PaymentResult refund(String cardNumber, BigDecimal amount) {
        // Эмуляция возврата средств
        return new PaymentResult(true, "Refund successful");
    }
}
