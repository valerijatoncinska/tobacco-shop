package de.shop.modules.users.repository.interfaces;

import de.shop.modules.users.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserEntityIdOrderByDateDesc(Long user);

    Optional<OrderEntity> findByUserEntityIdAndId(Long user, Long id);

}
