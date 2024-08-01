package de.shop.modules.orderHistory.domain;


import de.shop.modules.customer.domain.CustomerEntity;
import de.shop.modules.order.domain.entity.OrderEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_history")
public class OrderHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CustomerEntity customer;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "order_history_id"),
    inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<OrderEntity> orders;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderHistoryEntity that = (OrderHistoryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, orders);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return String.format("Order History with id %d belongs to customer with id %d and has %d orders", id, customer.getId(), orders.size());
    }

    public void addOrderToHistory(OrderEntity order){

        orders.add(order);
    }

    public void removeOrderById(Long id){

        orders.removeIf(order -> order.getId().equals(id));
    }

    public void clearOrderHistory(){

        orders.clear();
    }

}
