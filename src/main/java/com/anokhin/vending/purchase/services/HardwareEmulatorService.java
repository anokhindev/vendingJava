package com.anokhin.vending.purchase.services;

import com.anokhin.vending.purchase.utils.SensorCheckResult;
import com.anokhin.vending.vendingmachine.entity.Slot;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class HardwareEmulatorService {
    private final Random random = new Random();

    public void rotateSlot(Slot slot) {
        // Эмуляция прокрутки спирали
        try {
            Thread.sleep(1000); // Имитация времени прокрутки
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public SensorCheckResult checkSensor(Slot slot) {
        // Эмуляция проверки сенсора
        // В реальном приложении здесь был бы вызов API аппаратной части
        boolean success = random.nextDouble() > 0.2; // 80% успешных проверок
        
        if (success) {
            return new SensorCheckResult(true, "Товар успешно выдат");
        } else {
            return new SensorCheckResult(false, "Товар не упал");
        }
    }
}
