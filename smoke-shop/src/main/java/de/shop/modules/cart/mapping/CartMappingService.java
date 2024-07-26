package de.shop.modules.cart.mapping;

import de.shop.modules.cart.domain.CartDto;
import de.shop.modules.cart.domain.CartEntity;
import de.shop.modules.product.service.mapping.ProductMappingService;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = ProductMappingService.class)
public interface CartMappingService {

    CartDto mapEntityToDto(CartEntity entity);
}


