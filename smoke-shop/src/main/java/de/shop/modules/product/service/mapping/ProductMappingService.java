package de.shop.modules.product.service.mapping;

import de.shop.modules.product.domain.dto.ProductDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    ProductEntity mapDtoToEntity(ProductDto dto);

    ProductDto mapEntityToDto(ProductEntity entity);

}