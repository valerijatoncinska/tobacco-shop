package de.shop.core.ctrl_panel.domain.dto;

import de.shop.modules.users.domain.dto.OutputOrderNameDto;

/**
 * Наследник, который добавляет id пользователя
 */
public class OutputOrderNameAdminDto extends OutputOrderNameDto {
    private Long userId;

    public void setUserId(Long id) {
        this.userId = id;
    }

    public Long getUserId() {
        return userId;
    }

}