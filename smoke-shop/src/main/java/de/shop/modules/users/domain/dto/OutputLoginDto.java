package de.shop.modules.users.domain.dto;

/**
 * Класс, который возвращает данные для клиента после аутентификации
 */
public class OutputLoginDto {
    private String email;
    private String accessToken;
    private String refreshToken;

    public OutputLoginDto(String email, String accessToken, String refreshToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public OutputLoginDto() {

    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
