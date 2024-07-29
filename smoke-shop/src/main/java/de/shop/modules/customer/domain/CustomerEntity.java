package de.shop.modules.customer.domain;

import de.shop.modules.address.domain.AddressEntity;
import de.shop.modules.cart.domain.CartEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CartEntity cart;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private AddressEntity address;

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity entity = (CustomerEntity) o;
        return active == entity.active && Objects.equals(id, entity.id) && Objects.equals(name, entity.name) && Objects.equals(cart, entity.cart) && Objects.equals(address, entity.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active, cart, address);
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", cart=" + cart +
                ", address=" + address +
                '}';
    }
}