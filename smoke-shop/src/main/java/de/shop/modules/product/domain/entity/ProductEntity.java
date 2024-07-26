package de.shop.modules.product.domain.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be empty")
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "Title should contain at least 3 characters " +
                    "and start with capital letter/."
    )
    @Column(name = "title")
    private String title;

    @DecimalMin(
            value = "5.00",
            message = "Product price should be greater or equal than 5.00"
    )
    @DecimalMax(
            value = "100000.00",
            inclusive = false,
            message = "Product price should be lesser than 100000.00"
    )
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "active")
    private boolean active;

    @Column(name = "quantity")
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return active == that.active && quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, active, quantity);
    }

    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %s, active - %s, quantity - %d",
                id, title, price, active ? "yes" : "no", quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
