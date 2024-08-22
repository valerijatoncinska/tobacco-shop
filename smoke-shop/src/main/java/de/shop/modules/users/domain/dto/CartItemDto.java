package de.shop.modules.users.domain.dto;

import de.shop.modules.product.domain.entity.ProductEntity;

import java.math.BigDecimal;

public class CartItemDto {

    private Long id;

    private ProductEntity productEntity;

    private int quantity;

    private BigDecimal totalPrice;

    private int stock;

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return getProduct().getQuantity();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

