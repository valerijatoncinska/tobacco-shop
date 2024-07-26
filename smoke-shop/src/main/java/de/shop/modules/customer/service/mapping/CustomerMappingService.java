package de.shop.modules.customer.service.mapping;

import de.shop.modules.cart.mapping.CartMappingService;
import de.shop.modules.customer.domain.CustomerDto;
import de.shop.modules.customer.domain.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = CartMappingService.class
)
public interface CustomerMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "cart", ignore = true)
    CustomerEntity mapDtoToEntity(CustomerDto dto);

    CustomerDto mapEntityToDto(CustomerEntity entity);
}