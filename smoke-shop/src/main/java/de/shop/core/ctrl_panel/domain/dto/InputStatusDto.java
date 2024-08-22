package de.shop.core.ctrl_panel.domain.dto;

/**
 * Входящий DTO, который изменяет статус заказа
 */
public class InputStatusDto {
    private String status;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
