package de.shop.modules.users.service.mapping;

import de.shop.modules.users.domain.dto.InputRegDto;
import de.shop.modules.users.domain.dto.OutputRegDto;
import de.shop.modules.users.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс конвертации dto в entity и обратно.
 * Применяется при аутентификации, регистрации
 */
@Mapper(componentModel = "spring")
public interface AuthorMappingService {

    @Mapping(target = "id", ignore = true)
    UserEntity mapDtoToEntity(InputRegDto dto);

    OutputRegDto mapEntityToDto(UserEntity entity);

}
