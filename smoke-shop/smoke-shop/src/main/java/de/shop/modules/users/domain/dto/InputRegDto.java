package de.shop.modules.users.domain.dto;

/**
 * класс, принимающий данные с клиента для регистрации
 */
public class InputRegDto {
    private String email;
    private String password;

    public InputRegDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public InputRegDto() {

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}

