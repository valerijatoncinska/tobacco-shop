package de.shop.modules.cart.repository;

import de.shop.modules.cart.domain.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {


}
