package de.shop.modules.users.domain.dto;

/**
 * Класс, принимающий данные с клиента для обновления токенов
 */
public class InputRefreshTokenDto {
    private String refresh;

    public InputRefreshTokenDto(String refresh) {
        this.refresh = refresh;
    }

    public InputRefreshTokenDto() {

    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getRefresh() {
        return refresh;
    }
}
