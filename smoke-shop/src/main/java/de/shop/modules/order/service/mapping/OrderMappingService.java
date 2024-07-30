package de.shop.modules.order.service.mapping;

import de.shop.modules.order.domain.dto.OrderDto;
import de.shop.modules.order.domain.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = OrderMappingService.class
)

public interface OrderMappingService {

    OrderEntity mapDtoToEntity(OrderDto dto);

    OrderDto mapEntityToDto(OrderEntity entity);
}
