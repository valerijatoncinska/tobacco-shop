package de.shop.modules.order.domain.entity;

import de.shop.modules.users.domain.entity.UserEntity;
import jakarta.persistence.*;

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
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity productEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public OrderEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(OrderEntity productEntity) {
        this.productEntity = productEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderHistoryEntity that = (OrderHistoryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userEntity, that.userEntity) && Objects.equals(productEntity, that.productEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userEntity, productEntity);
    }
}
