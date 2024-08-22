package de.shop.modules.users.domain.dto;

/**
 * класс, принимающий данные с клиента для регистрации
 */
public class InputRegDto {
    private String email;
    private String password;
    private boolean isadult;
    private boolean subscribe_news;

    public InputRegDto(String email, String password, boolean isadult, boolean subscribe_news) {
        this.email = email;
        this.password = password;
        this.isadult = isadult;
        this.subscribe_news = subscribe_news;
    }


    public InputRegDto() {

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setIsAdult(boolean isadult) {
        this.isadult = isadult;
    }

    public boolean getIsAdult() {
        return isadult;
    }

    public void setSubscribeNews(boolean s) {
        this.subscribe_news = s;
    }

    public boolean getSubscribeNews() {
        return subscribe_news;
    }


}

