package de.shop.core.components;

import de.shop.core.exceptions.ValidateException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Кастомный класс валидации
 * Выполняет проверки входящих данных
 */
@Component
public class Validate {
    public Validate() {
    }

    /**
     * Метод, который проверяет, а корректно ли введена цена
     *
     * @param price   цена.
     * @param min     минимальный порог
     * @param max     максимальный порог
     * @param message сообщение ошибки
     * @return возвращает boolean
     * @throws ValidateException отлов ошибок
     */
    public boolean price(BigDecimal price, BigDecimal min, BigDecimal max, String message) throws ValidateException {
        boolean a = (price.compareTo(min) > 0 && price.compareTo(max) < 0);
        if (a == false) {
            throw new ValidateException(message);
        }
        return true;
    }

    /**
     * Метод, который проверяет корректность на минимальное и максимальное значение
     *
     * @param p       Число
     * @param min     минимальное значение
     * @param max     максимальное значение
     * @param message сообщение о ошибке
     * @return возвращает boolean
     * @throws ValidateException отлов ошибок
     */
    public boolean minMax(int p, int min, int max, String message) throws ValidateException {
        if (p > min && p < max) {
            return true;
        } else {
            throw new ValidateException(message);
        }
    }

    /**
     * Метод проверяет строку на пустоту
     *
     * @param str     строка для проверки
     * @param message сообщение, которое мы хотим вернуть клиенту.
     * @return возвращает boolean
     * @throws ValidateException перехватчик ошибок
     */
    public boolean notBlank(String str, String message) throws ValidateException {
        try {
            if (str == null || str.trim().isEmpty()) {
                throw new ValidateException(message);
            }
            return true;
        } catch (ValidateException e) {
            throw e;
        }
    }

    /**
     * метод возвращает true, если обязательное поле отмечено для дальнейшей работы
     *
     * @param b       значение, которое должно придти как boolean значение
     * @param message сообщение про ошибку
     * @return возвращает boolean
     * @throws ValidateException отлов ошибок
     */
    public boolean checked(boolean b, String message) throws ValidateException {
        if (!b) {
            throw new ValidateException(message);
        }
        return true;
    }


    /**
     * Метод выполняет проверку пароля перед изменением его или при регистрации
     *
     * @param str     строка с паролем, которая приходит от клиента как есть
     * @param min     минимальная длина
     * @param max     максимальная длина
     * @param message сообщение при ошибках, которое хотим вернуть клиенту
     * @return возвращает boolean
     * @throws ValidateException перехватчик ошибок
     */
    public boolean password(String str, int min, int max, String message) throws ValidateException {
        try {
            if (str == null || str.trim().isEmpty()) {
                throw new ValidateException(message);
            }
            if (str.length() < min || str.length() > max) {
                throw new ValidateException(message);
            }
            return true;
        } catch (ValidateException e) {
            throw e;
        }
    }

    /**
     * Метод, который проверяет, входящий email, на валидность.
     *
     * @param str     строка, содержащая email адрес
     * @param message сообщение о ошибке
     * @return возвращает boolean
     * @throws ValidateException перехвад ошибок
     */
    public boolean email(String str, String message) throws ValidateException {
        try {
            if (str == null || str.trim().isEmpty()) {
                throw new ValidateException(message);
            }
            String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                throw new ValidateException(message);
            }

            return true;
        } catch (ValidateException e) {
            throw e;
        }
    }

    /**
     * Метод проверяет диапозон почтового индекса.
     * Проверка привязана к немецкой системе.
     * Однако, проверка производится на высокий и низкий порог.
     * Это не решает проблему с такими индексами как 11111, 22222, 33333 и тд, которые не используются в реальности.
     *
     * @param code    строка в которой почтовый индекс. Пример: 55225 или 01057
     * @param message сообщение о ошибки
     * @return вернет boolean
     * @throws ValidateException отлов ошибок
     */
    public boolean postalCode(String code, String message) throws ValidateException {
        int c = Integer.parseInt(code);
        if (c < 1067 || c > 99998) {
            throw new ValidateException(message);
        }
        return true;
    }


}