package de.shop.modules.users.domain.dto;

import java.math.BigDecimal;

public class CartDto {
    private String title;
    private int quantity;
    private Long cartElement;
    public void setTitle(String title) {
        this.title = title;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setCartElement(Long id) {
        this.cartElement = id;
    }
    public String getTitle() {
        return title;
    }
public int getQuantity() {
        return quantity;
}
public Long getCartElement() {
        return cartElement;
}
}
