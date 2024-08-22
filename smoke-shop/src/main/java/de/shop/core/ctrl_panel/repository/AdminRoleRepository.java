package de.shop.core.ctrl_panel.repository;

import de.shop.modules.users.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий ролей для админа
 */
public interface AdminRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findById(Long id);
Optional<RoleEntity> findByTitle(String title);
}
