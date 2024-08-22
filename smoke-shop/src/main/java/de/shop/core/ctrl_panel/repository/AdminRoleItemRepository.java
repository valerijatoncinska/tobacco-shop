package de.shop.core.ctrl_panel.repository;

import de.shop.core.ctrl_panel.domain.entity.AdminRoleItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для связующих сущностей пользователя и ролей
 */
public interface AdminRoleItemRepository extends JpaRepository<AdminRoleItemEntity, Long> {
    Optional<AdminRoleItemEntity> findByUserIdAndId(Long user, Long id); // поиск связи между юзером и ролью. Поиск по юзеру и id записи

    Optional<AdminRoleItemEntity> findByUserIdAndRoleId(Long user, Long role); // поиск роли у пользователя по user_id и role_id

    List<AdminRoleItemEntity> findByUserId(Long id); // поиск всех ролей у пользователя.

    List<AdminRoleItemEntity> findByRoleId(Long id); // поиск роли у всех пользователей
}
