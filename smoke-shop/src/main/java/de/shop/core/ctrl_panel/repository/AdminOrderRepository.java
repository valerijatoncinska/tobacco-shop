package de.shop.core.ctrl_panel.repository;

import de.shop.modules.users.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminOrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findById(Long id); // показать заказ по id.

    List<OrderEntity> findByOrderStatusOrderByIdDesc(String status); // выборка по статусам.


}
