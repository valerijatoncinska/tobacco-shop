package de.shop.modules.product.domain.entity;


import de.shop.modules.users.domain.entity.CartItemEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "active")
    private boolean active;

    @Column(name = "quantity")
    private int quantity;
    @OneToMany(mappedBy = "productEntity")
    private Set<CartItemEntity> cartEntity;

    @Column(name = "description")
    private String description;

    @Column(name = "characteristics")
    private String characteristics;
    @Column(name = "img_url")
    private String imgUrl;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity entity = (ProductEntity) o;
        return active == entity.active && quantity == entity.quantity && Objects.equals(id, entity.id) && Objects.equals(title, entity.title) && Objects.equals(price, entity.price) && Objects.equals(cartEntity, entity.cartEntity) && Objects.equals(description, entity.description) && Objects.equals(characteristics, entity.characteristics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, active, quantity, cartEntity, description, characteristics);
    }

    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %s, active - %s, quantity - %d",
                id, title, price, active ? "yes" : "no", quantity);
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean getActive() {
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
