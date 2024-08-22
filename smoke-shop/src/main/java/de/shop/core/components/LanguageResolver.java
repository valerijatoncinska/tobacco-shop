package de.shop.core.components;

import de.shop.core.config.LanguageConfig;
import de.shop.core.exceptions.ParsePropertiesException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Properties;

/**
 * Данный класс управляет мультиязычностью проекта.
 */
@Component
public class LanguageResolver {
    @Value("${language.default}")
    private String languageDefault; // язык по умолчанию из application.properties
    @Value("${language.dir}")
    private String languageDir; // каталог для хранения переводов
    private ParseProperties parseProperties; // кастомная обертка для базового класса Properties
    private LanguageConfig languageConfig;

    public LanguageResolver(ParseProperties parseProperties, LanguageConfig languageConfig) {
        {
            this.parseProperties = parseProperties;
            this.languageConfig = languageConfig;
        }
    }

    /**
     * Метод будет выводить значение для языков.
     * Например: ru, de, en
     *
     * @return выводит строковое значение
     */
    public String getCurrentLang() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest(); // вызываем инструмент servlet для получения getпараметра в реальном времени
            String lang = request.getParameter("lang"); // получили значение из lang get параметра
            return (lang != null && !lang.isEmpty() && languageConfig.getData().contains(lang)) ? lang : languageDefault;
        }
        return languageDefault; // возвращаем язык по умолчанию, если запрос недоступен
    }

    /**
     * Метод загружает *.properties файл
     *
     * @param module имя модуля, для которого будет сделан вызов
     * @param file   имя файла, в котором есть языковая информация. Внимание, вводить без .properties
     * @return возвращает Properties
     * @throws ParsePropertiesException перехват ошибок. Класс лежит в exceptions
     */
    public Properties load(String module, String file) throws ParsePropertiesException {
        String path = languageDir + "/" + module + "/" + getCurrentLang() + "/" + file + ".properties";

        return parseProperties.getProperties(path);
    }
}
