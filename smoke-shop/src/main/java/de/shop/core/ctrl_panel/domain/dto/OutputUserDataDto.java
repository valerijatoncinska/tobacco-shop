package de.shop.core.ctrl_panel.domain.dto;

import java.time.LocalDateTime;

/**
 * Класс выводит информацию о пользователе для админ панели.
 */
public class OutputUserDataDto {
    private Long id;
    private String email;
    private LocalDateTime timeReg;
    private LocalDateTime timeVisit;
    private boolean isAdult;
    private boolean subscribeNews;
    private boolean emailActive;

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

    public void setTimeReg(LocalDateTime date) {
        this.timeReg = date;
    }

    public LocalDateTime getTimeReg() {
        return timeReg;
    }

    public void setTimeVisit(LocalDateTime date) {
        this.timeVisit = date;
    }

    public LocalDateTime getTimeVisit() {
        return timeVisit;
    }

    public void setIsAdult(boolean b) {
        this.isAdult = b;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setSubscribeNews(boolean b) {
        this.subscribeNews = b;
    }

    public boolean isSubscribeNews() {
        return subscribeNews;
    }

    public void setEmailActive(boolean b) {
        this.emailActive = b;
    }

    public boolean isEmailActive() {
        return emailActive;
    }


}
