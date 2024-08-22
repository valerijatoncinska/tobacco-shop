package de.shop.core.components;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Класс, который облегчает работу с датой.
 * Делает код проще и уменьшает избыточность.
 */
@Component
public class DateData {
    private ZoneId zoneId; // класс зон

    public DateData(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * Метод возвращает отформатированную дату в нужном формате
     *
     * @param pattern Строковое значение. Пример: dd.MM.yyyy
     * @return возвращает String, в котором будет нужный результат
     */
    public String format(String pattern) {
        LocalDateTime time = LocalDateTime.now(zoneId);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return time.format(format);
    }
}
