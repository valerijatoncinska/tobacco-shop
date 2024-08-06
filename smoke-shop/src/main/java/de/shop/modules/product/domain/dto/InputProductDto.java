package de.shop.modules.product.domain.dto;

import java.math.BigDecimal;

public class InputProductDto {
    private String title;
    private BigDecimal price;
    private int quantity;
    private boolean active;

    public InputProductDto(String title, BigDecimal price, int quantity, boolean active) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.active = active;
    }

    public InputProductDto() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int q) {
        this.quantity = q;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getActive() {
        return active;
    }
}
