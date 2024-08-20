package de.shop.core.ctrl_panel.domain.dto;

/**
 * Класс выводит информацию про роль
 */
public class OutputRoleDto {
    private Long id;
    private String title;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
