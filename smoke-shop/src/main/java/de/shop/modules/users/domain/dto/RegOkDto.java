package de.shop.modules.users.domain.dto;

public class RegOkDto {
    private String message;

    public RegOkDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
