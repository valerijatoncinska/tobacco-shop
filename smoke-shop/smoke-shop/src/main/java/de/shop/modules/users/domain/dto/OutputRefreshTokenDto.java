package de.shop.modules.users.domain.dto;

/**
 * Класс, возвращающий данные для клиента с обновленными данными для refresh и access token
 */
public class OutputRefreshTokenDto {
    private String email;
    private String accessToken;
    private String refreshToken;

    public OutputRefreshTokenDto(String email, String accessToken, String refreshToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public OutputRefreshTokenDto() {

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
