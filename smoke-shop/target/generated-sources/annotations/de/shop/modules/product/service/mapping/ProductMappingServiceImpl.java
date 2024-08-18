package de.shop.modules.product.service.mapping;

import de.shop.modules.product.domain.dto.InputProductDto;
import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.domain.dto.OutputProductUserDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T21:31:11+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class ProductMappingServiceImpl implements ProductMappingService {

    @Override
    public ProductEntity mapDtoToEntity(InputProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setCharacteristics( dto.getCharacteristics() );
        productEntity.setDescription( dto.getDescription() );
        productEntity.setQuantity( dto.getQuantity() );
        productEntity.setTitle( dto.getTitle() );
        productEntity.setPrice( dto.getPrice() );
        productEntity.setActive( dto.getActive() );

        return productEntity;
    }

    @Override
    public OutputProductAdminDto mapEntityToAdminDto(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OutputProductAdminDto outputProductAdminDto = new OutputProductAdminDto();

        outputProductAdminDto.setId( entity.getId() );
        outputProductAdminDto.setTitle( entity.getTitle() );
        outputProductAdminDto.setDescription( entity.getDescription() );
        outputProductAdminDto.setCharacteristics( entity.getCharacteristics() );
        outputProductAdminDto.setPrice( entity.getPrice() );
        outputProductAdminDto.setQuantity( entity.getQuantity() );
        outputProductAdminDto.setActive( entity.getActive() );

        return outputProductAdminDto;
    }

    @Override
    public OutputProductUserDto mapEntityToUserDto(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OutputProductUserDto outputProductUserDto = new OutputProductUserDto();

        outputProductUserDto.setId( entity.getId() );
        outputProductUserDto.setTitle( entity.getTitle() );
        outputProductUserDto.setPrice( entity.getPrice() );
        outputProductUserDto.setDescription( entity.getDescription() );
        outputProductUserDto.setCharacteristics( entity.getCharacteristics() );

        return outputProductUserDto;
    }
}
