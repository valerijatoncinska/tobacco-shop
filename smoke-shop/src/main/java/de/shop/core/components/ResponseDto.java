package de.shop.core.components;

import org.springframework.stereotype.Component;

/**
 * Класс, который возвращает ответ для клиента
 * Было решено сделать одно место, которое вернет ответ в одном виде.
 *
 * @param <T>
 */
@Component
public class ResponseDto<T> {

    private T data; // Данные
    private String message; // дополнительное сообщение
    private String lang; // Язык


    public ResponseDto(boolean success, T data, String message, String lang) {
        this.lang = lang;
        this.data = data;
        this.message = message;
    }

    public ResponseDto() {

    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getLang() {
        return lang;
    }

    public T getData() {
        return data;
    }


}
