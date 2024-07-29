package de.shop.modules.order.domain.dto;

import de.shop.modules.order.domain.OrderStatus;
import de.shop.modules.product.domain.entity.ProductEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OrderDto {

    private Long id;

    private List<ProductEntity> products;

    private BigDecimal totalPrice;

    private int totalQuantity;

    private LocalDate orderDate;

    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return totalQuantity == orderDto.totalQuantity && Objects.equals(id, orderDto.id) && Objects.equals(products, orderDto.products) && Objects.equals(totalPrice, orderDto.totalPrice) && Objects.equals(orderDate, orderDto.orderDate) && status == orderDto.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, totalPrice, totalQuantity, orderDate, status);
    }
}
