package de.shop.modules.cart.domain;


import de.shop.modules.customer.domain.CustomerEntity;
import de.shop.modules.product.domain.entity.ProductEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CustomerEntity customer;

    @ManyToMany
    @JoinTable(name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductEntity> products;

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

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartEntity that = (CartEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, products);
    }

    @Override
    public String toString() {
        return String.format("Cart with id - %d, has %d products", id, products != null ? products.size() : 0);
    }

    public void addProduct(ProductEntity product) {
        if (product.isActive()) {
            products.add(product);
        }
    }
    public BigDecimal getCartTotalCost() {
        return products.stream().filter(ProductEntity::isActive).map(ProductEntity::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
    public List<ProductEntity> getAllActiveProducts() {
        return products.stream().filter(ProductEntity::isActive).toList();
    }
    public void removeProductById(Long productId) {

        products.removeIf(product -> product.getId().equals(productId));
    }
    public void deleteAllProducts() {
        products.clear();
    }
}
