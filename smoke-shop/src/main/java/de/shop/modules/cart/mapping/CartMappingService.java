package de.shop.modules.cart.mapping;

import de.shop.modules.cart.domain.CartDto;
import de.shop.modules.cart.domain.CartEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = CartMappingService.class)
public interface CartMappingService {

    CartDto mapEntityToDto(CartEntity entity);
}


