package de.shop.modules.users.service.mapping;

import de.shop.modules.users.domain.dto.OutputOrderDataDto;
import de.shop.modules.users.domain.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMappingService {


    @Mapping(target = "total", source = "total") // Маппинг поля 'total'
    @Mapping(target = "orderStatus", source = "orderStatus")
        // Маппинг поля 'orderStatus'
    OutputOrderDataDto mapEntityToDto(OrderEntity entity);
}
