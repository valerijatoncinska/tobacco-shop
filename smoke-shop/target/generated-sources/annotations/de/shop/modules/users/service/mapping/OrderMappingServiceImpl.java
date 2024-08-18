package de.shop.modules.users.service.mapping;

import de.shop.modules.users.domain.dto.OutputOrderDataDto;
import de.shop.modules.users.domain.entity.OrderEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T21:31:11+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class OrderMappingServiceImpl implements OrderMappingService {

    @Override
    public OutputOrderDataDto mapEntityToDto(OrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OutputOrderDataDto outputOrderDataDto = new OutputOrderDataDto();

        outputOrderDataDto.setTotal( entity.getTotal() );
        outputOrderDataDto.setOrderStatus( entity.getOrderStatus() );
        outputOrderDataDto.setEmail( entity.getEmail() );
        outputOrderDataDto.setPhone( entity.getPhone() );
        outputOrderDataDto.setId( entity.getId() );
        outputOrderDataDto.setDate( entity.getDate() );
        outputOrderDataDto.setPayments( entity.getPayments() );
        outputOrderDataDto.setDeliveryAddress( entity.getDeliveryAddress() );
        outputOrderDataDto.setBillingAddress( entity.getBillingAddress() );

        return outputOrderDataDto;
    }
}
