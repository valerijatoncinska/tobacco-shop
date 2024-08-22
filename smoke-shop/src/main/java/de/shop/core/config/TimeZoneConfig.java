package de.shop.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;

/**
 * Конфигурационный класс, который настраивает часовой пояс
 */
@Configuration
public class TimeZoneConfig {
    private String idZone; // для id часового пояса

    public TimeZoneConfig(@Value("${spring.timezone.id}") String idZone) {
        this.idZone = idZone;
    }

    /**
     * Метод возвращает часовой пояс для классов, которые работают с датой и временем
     *
     * @return возвращает ZoneId
     */
    @Bean
    public ZoneId defaultZoneId() {
        return ZoneId.of(idZone);
    }
}
