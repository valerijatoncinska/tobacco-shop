package de.shop.modules.product.repository.interfaces;

import de.shop.modules.product.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByTitle(String title);

    Optional<ProductEntity> findById(Long id);


}
