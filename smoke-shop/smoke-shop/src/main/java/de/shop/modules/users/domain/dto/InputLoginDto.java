package de.shop.modules.users.domain.dto;

/**
 * Класс, принимающий входящие данные с клиента для аутентификации
 */
public class InputLoginDto {
    private String email;
    private String password;

    public InputLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public InputLoginDto() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
