package de.shop.modules.orderHistory.domain;

import de.shop.modules.order.domain.entity.OrderEntity;

import java.util.List;
import java.util.Objects;

public class OrderHistoryDto {

    private Long id;

    private List<OrderEntity> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderHistoryDto that = (OrderHistoryDto) o;
        return Objects.equals(id, that.id) && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orders);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return String.format("Order History with id %d has %d orders", id, orders.size());
    }
}
