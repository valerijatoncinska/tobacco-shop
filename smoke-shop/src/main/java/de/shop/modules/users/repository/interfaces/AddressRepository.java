package de.shop.modules.users.repository.interfaces;

import de.shop.modules.users.domain.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для адресов пользователя
 */
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findByUserEntityId(Long id); // список адресов

    Optional<AddressEntity> findByUserEntityIdAndId(Long userId, Long id); // один адрес

    void deleteById(Long id); // удаление адреса
}
