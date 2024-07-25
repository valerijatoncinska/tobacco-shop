package de.shop.modules.users.repository.interfaces;

import de.shop.modules.users.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Интерфейс для работы с базой данных, с таблицей RoleEntity
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByTitle(String title);

}
