package de.shop.core.ctrl_panel.domain.dto;

import java.time.LocalDateTime;

/**
 * Класс, который выводит краткую информацию о пользователе в списке пользователей.
 */
public class OutputUserPreviewDto {
    private Long id;
    private String email;
    private LocalDateTime timeVisit;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTimeVisit(LocalDateTime date) {
        this.timeVisit = date;
    }

    public LocalDateTime getTimeVisit() {
        return timeVisit;
    }
}
