package de.shop.modules.cart.mapping;

import de.shop.modules.cart.domain.CartDto;
import de.shop.modules.cart.domain.CartEntity;
import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.service.mapping.ProductMappingService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMappingService.class)
public interface CartMappingService {

    CartDto mapEntityToDto(CartEntity entity);
}


