package de.shop.modules.users.domain.entity;

import de.shop.modules.product.domain.entity.ProductEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    @Column(name = "quantity")
    private int quantity;

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity ent) {
        this.userEntity = ent;
    }

    public ProductEntity getProduct() {
        return productEntity;
    }

    public void setProduct(ProductEntity entity) {
        this.productEntity = entity;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getUserId() {
        return (userEntity != null) ? userEntity.getId() : null;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return getProduct().getQuantity();
    }
}

