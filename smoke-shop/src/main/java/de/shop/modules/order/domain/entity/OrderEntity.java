package de.shop.modules.order.domain.entity;

import de.shop.modules.order.domain.OrderStatus;
import de.shop.modules.product.domain.entity.ProductEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @NotNull(message = "Product list cannot be null")
    private List<ProductEntity> products;

    @DecimalMin(
            value = "0.01",
            message = "Total price should be greater or equal than 0.01"
    )
    @DecimalMax(
            value = "100000.00",
            inclusive = false,
            message = "Total price should be lesser than 100000.00"
    )
    @Column(name = "price")
    private BigDecimal totalPrice;

    @Max(99)
    @Min(1)
    @Column(name = "total_quantity")
    private int totalQuantity;

    @NotNull
    @NotEmpty
    private LocalDate orderDate;

    @NotNull
    private OrderStatus status;

    public OrderEntity() {

    }

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
        OrderEntity that = (OrderEntity) o;
        return totalQuantity == that.totalQuantity && Objects.equals(id, that.id) && Objects.equals(products, that.products) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(orderDate, that.orderDate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, totalPrice, totalQuantity, orderDate, status);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", products=" + products +
                ", totalPrice=" + totalPrice +
                ", totalQuantity=" + totalQuantity +
                ", orderDate=" + orderDate +
                ", status=" + status +
                '}';
    }
}

