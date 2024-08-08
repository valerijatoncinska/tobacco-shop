package de.shop.modules.users.jwt;

/**
 * Класс, для хранения user, чтобы им можно было воспользоваться везде
 */
public class UserObject {
    private Long id;
    private String email;

    public UserObject(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;


    }
}

