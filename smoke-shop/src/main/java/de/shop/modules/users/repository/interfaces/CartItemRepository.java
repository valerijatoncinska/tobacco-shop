package de.shop.modules.users.repository.interfaces;

import de.shop.modules.users.domain.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByUserEntityId(Long id); // вернет список товаров в корзине

    Optional<CartItemEntity> findByUserEntityIdAndProductEntityId(Long user_id, Long product_id); // находит товары по пользователю  и продукту

    Optional<CartItemEntity> findById(Long id); // находит элемент корзины


    void deleteByProductEntity_Id(Long id);
}
