package de.shop.modules.users.service.mapping;

import de.shop.modules.users.domain.dto.CartItemDto;
import de.shop.modules.users.domain.entity.CartItemEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CartItemMappingService {
    //    @Mapping(target = "id", ignore = true)
    CartItemEntity mapDtoToEntity(CartItemDto dto);

    CartItemDto mapEntityToDto(CartItemEntity entity);
}
