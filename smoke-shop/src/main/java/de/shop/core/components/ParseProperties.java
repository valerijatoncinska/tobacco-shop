package de.shop.core.components;

import de.shop.core.exceptions.ParsePropertiesException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Класс, который занимается парсингом файлов .properties
 * Почему создан свой вариант?
 * Ответ прост, реализация, от spring boot, не подходит, так как подгрузка идет всех файлов сразу и хранится в памяти постоянно.
 * А что если у нас сотня таких файлов.
 * Возможно, в силу поверхносного знакомства, я не имею полного представления о возможностях данного фреймворка, но на это нет времени.
 */
@Component
public class ParseProperties implements ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    public ParseProperties(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Метод для открытия файлов .properties, которые находятся в каталоге resource
     *
     * @param path путь к файлу
     * @return возвращает Properties
     * @throws ParsePropertiesException
     */
    public Properties getProperties(String path) throws ParsePropertiesException {
        Properties p = new Properties(); // базовый класс Properties
        try {
            Resource resource = resourceLoader.getResource("classpath:" + path); // загрузка ресурса
            try (InputStream inputStream = resource.getInputStream()) { // загружаем данные
                try (InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8")) { // читаем данные
                    p.load(reader); // загружаем в Properties

                }
            }
        } catch (IOException e) { // обработка ошибки
            e.printStackTrace();
            throw new ParsePropertiesException("The configuration file is missing: " + path, e);
        }
        return p;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
