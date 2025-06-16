package com.anokhin.vending.purchase.servises;

import com.anokhin.vending.vendingmachine.entity.Slot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class HardwareEmulatorService {
    private final Random random = new Random();

    public void spin(Slot slot) {
        log.info("Прокрутка спирали ячейки {}", slot.getId());
    }

    public boolean checkFallSensor(Slot slot) {
        boolean result = random.nextBoolean();
        log.info("Проверка падения товара в ячейке {}: {}", slot.getId(), result);
        return result;
    }
}
