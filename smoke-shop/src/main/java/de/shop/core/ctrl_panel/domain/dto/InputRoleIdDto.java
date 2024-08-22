package de.shop.core.ctrl_panel.domain.dto;

/**
 * Входящий DTO, который содержит id роли, которая используется для добавление ролей пользователям.
 */
public class InputRoleIdDto {
    private Long role;

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getRole() {
        return role;
    }
}
