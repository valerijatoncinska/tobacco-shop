package de.shop.core.ctrl_panel.domain.dto;

import java.util.List;

/**
 * Класс, который выводит полную информацию о пользователе в админ панели, а так же роли к ним.
 */
public class OutputUserDto {
    private OutputUserDataDto data;
    private List<OutputRoleDto> roles;

    public void setData(OutputUserDataDto data) {
        this.data = data;
    }

    public OutputUserDataDto getData() {
        return data;
    }

    public void setRoles(List<OutputRoleDto> roles) {
        this.roles = roles;
    }

    public List<OutputRoleDto> getRoles() {
        return roles;
    }
}
