package de.shop.core.components;

import de.shop.core.exceptions.ValidateException;
import org.springframework.stereotype.Component;

import java.util.Properties;
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
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
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

}
