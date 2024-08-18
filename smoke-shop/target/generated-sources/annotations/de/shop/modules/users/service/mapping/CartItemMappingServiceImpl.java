package de.shop.modules.users.service.mapping;

import de.shop.modules.users.domain.dto.CartItemDto;
import de.shop.modules.users.domain.entity.CartItemEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T21:31:11+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class CartItemMappingServiceImpl implements CartItemMappingService {

    @Override
    public CartItemEntity mapDtoToEntity(CartItemDto dto) {
        if ( dto == null ) {
            return null;
        }

        CartItemEntity cartItemEntity = new CartItemEntity();

        cartItemEntity.setProduct( dto.getProduct() );
        cartItemEntity.setQuantity( dto.getQuantity() );

        return cartItemEntity;
    }

    @Override
    public CartItemDto mapEntityToDto(CartItemEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CartItemDto cartItemDto = new CartItemDto();

        cartItemDto.setProduct( entity.getProduct() );
        cartItemDto.setQuantity( entity.getQuantity() );
        cartItemDto.setId( entity.getId() );
        cartItemDto.setStock( entity.getStock() );

        return cartItemDto;
    }
}
