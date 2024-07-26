package de.shop.modules.customer.repository;

import de.shop.modules.customer.domain.CustomerEntity;
import de.shop.modules.product.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByName(String name);
    Optional<CustomerEntity> findById(String id);
}
