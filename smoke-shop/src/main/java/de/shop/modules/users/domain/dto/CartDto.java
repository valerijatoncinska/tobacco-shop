package de.shop.modules.users.domain.dto;

import java.math.BigDecimal;

public class CartDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private int quantity;
private int stock;
private Long productId;
    public void setTitle(String title) {
        this.title = title;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
public int getQuantity() {
        return quantity;
}
public Long getId() {
        return id;
}
public int getStock() {
        return stock;
}
public void setStock(int stock) {
        this.stock = stock;
}
public void setProductId(Long productId) {
        this.productId = productId;
}
public Long getProductId() {
        return productId;
}
public BigDecimal getPrice() {
        return price;
}
public void setPrice(BigDecimal price) {
        this.price = price;
}

}
