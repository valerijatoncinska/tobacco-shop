package de.shop.modules.product.domain.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class OutputProductAdminDto {

    private Long id;
    private String title;
    private BigDecimal price;
private int quantity;
private boolean active;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setQuantity(int quantity) { this.quantity = quantity;}
    public int getQuantity() { return quantity;}
    public void setActive(boolean active) { this.active = active;}
    public boolean getActive() { return active;}

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price);
    }

    @Override
    public String toString() {
        return "Product:" + title + " price:" + price;
    }
}