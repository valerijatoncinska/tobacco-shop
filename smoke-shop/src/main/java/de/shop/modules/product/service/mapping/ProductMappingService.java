package de.shop.modules.product.service.mapping;

import de.shop.modules.product.domain.dto.InputProductDto;
import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.domain.dto.OutputProductUserDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMappingService {

    @Mapping(target = "id", ignore = true)

    ProductEntity mapDtoToEntity(InputProductDto dto);

    OutputProductAdminDto mapEntityToAdminDto(ProductEntity entity);

    OutputProductUserDto mapEntityToUserDto(ProductEntity entity);


}