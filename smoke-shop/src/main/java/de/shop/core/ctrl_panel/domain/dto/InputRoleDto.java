package de.shop.core.ctrl_panel.domain.dto;

/**
 * DTO для входящих данных, чтобы создать роль
 */
public class InputRoleDto {
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
