package de.shop.core.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Конфигурационный класс, который загружает и управляет списком языков.
 */
@Configuration
public class LanguageConfig {

    private Set<String> langMap = new HashSet<>(); // Сбор языковых меток
    private final Environment env; // Для обновления языков

    @Value("${language.list}")
    private String languageList; // Список языков из конфигурационного файла

    public LanguageConfig(Environment env) {
        this.env = env;
    }

    /**
     * Инициализация языков при запуске приложения.
     */
    @PostConstruct
    public void init() {
        updateData();
    }

    /**
     * Динамическое обновление списка языков.
     */
    public void updateData() {
        // Обновляем язык на основе текущего значения в Environment
        String newLanguageList = env.getProperty("language.list");
        if (newLanguageList != null) {
            langMap = new HashSet<>(Arrays.asList(newLanguageList.split(",")));
        }
    }

    /**
     * Возвращает список языков как строку из конфигурационного файла.
     */
    public String getLanguageList() {
        return languageList;
    }

    /**
     * Возвращает Set языков.
     *
     * @return возвращает Set<String>
     */
    public Set<String> getData() {
        return langMap;
    }
}
