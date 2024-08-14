package de.shop.modules.users.repository.interfaces;

import de.shop.modules.users.domain.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findByOrderEntityIdOrderByIdDesc(Long order);
}
