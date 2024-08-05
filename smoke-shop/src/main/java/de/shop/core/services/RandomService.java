package de.shop.core.services;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

/**
 * Класс, который выдает случайные значения
 */
@Service
public class RandomService {
    private Random random = new Random();

    /**
     * Метод, выдающий случайное число
     *
     * @param min минимальное значение
     * @param max максимальное значение
     * @return возвращает int значение
     */
    public int rand(int min, int max) {
        if (min > max) {
            return 0;
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Метод выдает случайный uuid
     *
     * @return возвращает строку
     */
    public String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
