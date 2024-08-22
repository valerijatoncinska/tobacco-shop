package de.shop.modules.product.domain.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class InputProductDto {
    private String title;
    private BigDecimal price;
    private int quantity;
    private boolean active;
    private String description;
    private String characteristics;
    private String imgUrl;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public boolean isActive() {
        return active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputProductDto that = (InputProductDto) o;
        return quantity == that.quantity && active == that.active && Objects.equals(title, that.title) && Objects.equals(price, that.price) && Objects.equals(description, that.description) && Objects.equals(characteristics, that.characteristics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, quantity, active, description, characteristics);
    }

    @Override
    public String toString() {
        return "InputProductDto{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", active=" + active +
                ", description='" + description + '\'' +
                ", characteristics='" + characteristics + '\'' +
                '}';
    }
}
