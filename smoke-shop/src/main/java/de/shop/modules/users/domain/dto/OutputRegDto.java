package de.shop.modules.users.domain.dto;

/**
 * Класс, который возвращает данные для клиента после успешной регистрации
 */
public class OutputRegDto {
    private Long id;
    private String email;

    public OutputRegDto() {

    }

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


}